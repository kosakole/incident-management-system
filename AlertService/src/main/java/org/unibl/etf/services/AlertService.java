package org.unibl.etf.services;

import org.unibl.etf.models.dto.AlertSettings;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import reactor.core.publisher.Flux;

public interface AlertService {
    void updateSettings(AlertSettings settings);
    Flux<IncidentResponse> getAlertStream();
}
