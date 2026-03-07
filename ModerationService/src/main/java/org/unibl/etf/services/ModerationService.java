package org.unibl.etf.services;

import org.unibl.etf.models.dto.responses.IncidentResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ModerationService {
    Mono<Void> accept(Long id);
    Mono<Void> reject(Long id);
    Mono<List<IncidentResponse>> getNewIncidents();
}
