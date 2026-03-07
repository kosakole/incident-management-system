package org.unibl.etf.services.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.IncidentService;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {



    private final WebClient webClient;

    public IncidentServiceImpl(WebClient.Builder webClient, @Value("${app.url.incidents}") String url) {
        this.webClient = webClient.baseUrl(url).build();
    }

    @Override
    public Mono<Void> accept(Long id) {
        return this.webClient.patch().uri("/{id}/accept", id).retrieve().bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> reject(Long id) {
        return this.webClient.patch().uri("/{id}/reject", id).retrieve().bodyToMono(Void.class);
    }

    @Override
    public Mono<List<IncidentResponse>> getNewIncidents() {
        return this.webClient.get().uri("/new").retrieve().bodyToMono(new ParameterizedTypeReference<List<IncidentResponse>>() {});
    }
}
