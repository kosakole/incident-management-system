package org.unibl.etf.services.implementation;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.models.dto.City;
import org.unibl.etf.models.dto.Location;
import org.unibl.etf.models.dto.requests.FilterRequest;
import org.unibl.etf.models.dto.requests.IncidentRequest;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.models.entities.IncidentEntity;
import org.unibl.etf.models.enums.IncidentStatus;
import org.unibl.etf.models.exceptions.NotFoundException;
import org.unibl.etf.repositories.IncidentRepository;
import org.unibl.etf.services.CityService;
import org.unibl.etf.services.ImageService;
import org.unibl.etf.services.IncidentBroadcaster;
import org.unibl.etf.services.IncidentService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Value("${app.images.url}")
    private String imagesURL;
    private final IncidentRepository incidentRepository;
    private final ModelMapper mapper;
    private final CityService cityService;
    private final ImageService imageService;
    private final IncidentBroadcaster incidentBroadcaster;

    @PersistenceContext
    private final EntityManager entityManager;

    public IncidentServiceImpl(IncidentRepository incidentRepository, ModelMapper mapper, CityService cityService, ImageService imageService, IncidentBroadcaster incidentBroadcaster, EntityManager entityManager) {
        this.incidentRepository = incidentRepository;
        this.mapper = mapper;
        this.cityService = cityService;
        this.imageService = imageService;
        this.incidentBroadcaster = incidentBroadcaster;
        this.entityManager = entityManager;
    }


    @Override
    public List<IncidentResponse> getAll() {
        return incidentRepository.findAll().stream().map(e -> getIncidentRespose(e)).collect(Collectors.toList());
    }

    @Override
    public List<IncidentResponse> getNewIncidents() {
        return incidentRepository.findByStatus(IncidentStatus.NEW).stream().map(inc -> getIncidentRespose(inc)).collect(Collectors.toList());
    }

    @Override
    public List<IncidentResponse> getAcceptedIncidents() {
        return incidentRepository.findByStatus(IncidentStatus.ACCEPTED).stream().map(inc ->  getIncidentRespose(inc)).collect(Collectors.toList());
    }

    @Override
    public List<IncidentResponse> getAcceptedIncidents(FilterRequest request) {
        request.setStatus(IncidentStatus.ACCEPTED);
        return filter(request);
    }

    @Override
    public IncidentResponse get(Long id) {
        return getIncidentRespose(incidentRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    @Transactional
    public IncidentResponse create(IncidentRequest incident, MultipartFile image) {
        IncidentEntity entity = getIncidentEntity(incident);
        try {
            entity.setImage(imageService.save(image));
        }catch (Exception ex){
            ex.getStackTrace();
        }
        entity.setStatus(IncidentStatus.NEW);
        entity.setReportedAt(LocalDateTime.now());
        IncidentResponse response = getIncidentRespose(incidentRepository.saveAndFlush(entity));
        incidentBroadcaster.publish(response);
        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        IncidentEntity entity = incidentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        incidentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void accept(Long id) {
        IncidentEntity entity = incidentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        entity.setStatus(IncidentStatus.ACCEPTED);
        incidentRepository.saveAndFlush(entity);
    }

    @Override
    public void reject(Long id) {
        IncidentEntity entity = incidentRepository.findById(id).orElse(null);
        if (entity == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        entity.setStatus(IncidentStatus.REJECTED);
        incidentRepository.saveAndFlush(entity);
    }

    private IncidentEntity getIncidentEntity(IncidentRequest incident) {
        IncidentEntity entity = mapper.map(incident, IncidentEntity.class);
        entity.setLatitude(incident.getLocation().getLatitude());
        entity.setLongitude(incident.getLocation().getLongitude());
        return entity;
    }

    private IncidentResponse getIncidentRespose(IncidentEntity entity) {
        IncidentResponse response = mapper.map(entity, IncidentResponse.class);
        response.setLocation(new Location(entity.getLatitude(), entity.getLongitude()));
        response.setImage(imagesURL + response.getImage());
        return response;

    }

    private List<IncidentResponse> filter(@NonNull FilterRequest filterRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<IncidentEntity> query = cb.createQuery(IncidentEntity.class);
        Root<IncidentEntity> root = query.from(IncidentEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filterRequest.getType() != null) {
            predicates.add(cb.equal(root.get("type"), filterRequest.getType()));
        }

        if (filterRequest.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filterRequest.getStatus()));
        }

        if (filterRequest.getCity() != null) {
            City city = cityService.getByName(filterRequest.getCity());
            Predicate latitudePredicate = cb.le(
                    cb.abs(
                            cb.diff(root.get("latitude"), city.getLocation().getLatitude()
                            )
                    ), city.getRange()
            );
            Predicate longitudePredicate = cb.le(
                    cb.abs(
                            cb.diff(root.get("longitude"), city.getLocation().getLongitude()
                            )
                    ), city.getRange()
            );
            predicates.add(cb.and(latitudePredicate, longitudePredicate));
        }

        if (filterRequest.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("reportedAt"), filterRequest.getStartDate()));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));

        TypedQuery<IncidentEntity> typedQuery = entityManager.createQuery(query);

        List<IncidentEntity> incidentEntities = typedQuery.getResultList();
        List<IncidentResponse> incidents = incidentEntities.stream()
                .map(e -> getIncidentRespose(e))
                .collect(Collectors.toList());
        return incidents;
    }
}
