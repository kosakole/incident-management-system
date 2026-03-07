package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.IncidentService;
import org.unibl.etf.services.ModerationService;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ModerationServiceimpl implements ModerationService {

    private final IncidentService incidentService;

    public ModerationServiceimpl(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @Override
    public Mono<Void> accept(Long id) {
        return incidentService.accept(id);
    }

    @Override
    public Mono<Void> reject(Long id) {
        return incidentService.reject(id);
    }

    @Override
    public Mono<List<IncidentResponse>> getNewIncidents() {
        return incidentService.getNewIncidents();
    }


}
