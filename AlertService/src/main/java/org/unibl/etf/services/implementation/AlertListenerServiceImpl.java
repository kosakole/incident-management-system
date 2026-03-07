package org.unibl.etf.services.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.AlertListenerService;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class AlertListenerServiceImpl implements AlertListenerService {

    private final WebClient webClient;

    public AlertListenerServiceImpl(WebClient.Builder builder, @Value("${app.url.incidents}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }

    public Flux<IncidentResponse> getIncidentStream() {
        return webClient.get()
                .uri("/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(IncidentResponse.class)
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)));

    }

}
