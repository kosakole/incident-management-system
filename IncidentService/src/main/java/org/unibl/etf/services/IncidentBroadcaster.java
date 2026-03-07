package org.unibl.etf.services;

import org.unibl.etf.models.dto.responses.IncidentResponse;
import reactor.core.publisher.Flux;

public interface IncidentBroadcaster {
    void publish(IncidentResponse incident);
    Flux<IncidentResponse> getIncidentStream();
}
