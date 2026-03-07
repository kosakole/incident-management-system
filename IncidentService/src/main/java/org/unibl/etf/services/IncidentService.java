package org.unibl.etf.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.models.dto.requests.FilterRequest;
import org.unibl.etf.models.dto.requests.IncidentRequest;
import org.unibl.etf.models.dto.responses.IncidentResponse;

import java.util.List;

public interface IncidentService {

    List<IncidentResponse> getAll();
    List<IncidentResponse> getNewIncidents();
    List<IncidentResponse> getAcceptedIncidents();
    List<IncidentResponse> getAcceptedIncidents(FilterRequest request);
    IncidentResponse get(Long id);
    IncidentResponse create(IncidentRequest incident, MultipartFile image);
    void delete(Long id);
    void accept(Long id);
    void reject(Long id);

}
