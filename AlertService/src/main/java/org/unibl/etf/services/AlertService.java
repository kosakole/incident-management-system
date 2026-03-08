package org.unibl.etf.services;

import org.unibl.etf.configs.AlertConfig;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import reactor.core.publisher.Flux;

public interface AlertService {
    void updateSettings(AlertConfig settings);
    Flux<IncidentResponse> getAlertStream();
}
