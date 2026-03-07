package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.City;
import org.unibl.etf.models.dto.responses.AnalysisResponse;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.models.enums.IncidentType;
import org.unibl.etf.services.AnalyticsService;
import org.unibl.etf.services.CityService;
import org.unibl.etf.services.IncidentService;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final IncidentService incidentService;
    private final CityService cityService;

    public AnalyticsServiceImpl(IncidentService incidentService, CityService cityService) {
        this.incidentService = incidentService;
        this.cityService = cityService;
    }

    @Override
    public Mono<AnalysisResponse> getAnalysis() {
        Mono<List<IncidentResponse>> cachedIncidents = incidentService.getAll().cache();
        List<City> cities = cityService.getAll();
        AnalysisResponse analysisResponse = new AnalysisResponse();
        Mono<Map<LocalDate, Long>> countByDate = cachedIncidents.map(list -> list.stream().collect(Collectors.groupingBy( inc -> inc.getReportedAt().toLocalDate() , Collectors.counting())));
        Mono<Map<String, Long>>  countByCity = cachedIncidents
                .map(list -> list.stream().collect(Collectors.groupingBy(
                        inc -> {
                            return cities.stream().filter(
                                    city -> city.isLocationInCity(inc.getLocation())).map(City::getName).findFirst().orElse("Unknown");

                        }, Collectors.counting()
                )));
        Mono<Map<IncidentType, Long>> countByType = cachedIncidents.map(list -> list.stream().collect(Collectors.groupingBy(inc -> inc.getType() , Collectors.counting())));
        return Mono.zip(countByDate, countByCity, countByType)
                .map(tuple -> AnalysisResponse.builder()
                        .analysisByDate(tuple.getT1())
                        .analysisByCity(tuple.getT2())
                        .analysisByType(tuple.getT3()).build()
                );
    }

}
