package org.unibl.etf.services;

import org.unibl.etf.models.dto.responses.IncidentResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IncidentService {

    Mono<List<IncidentResponse>> getAll();
}
