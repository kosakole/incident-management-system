package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.IncidentBroadcaster;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class IncidentBroadcasterImpl implements IncidentBroadcaster {

    private final Sinks.Many<IncidentResponse> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void publish(IncidentResponse incident) {
        sink.tryEmitNext(incident);
    }

    public Flux<IncidentResponse> getIncidentStream() {
        return sink.asFlux();
    }
}
