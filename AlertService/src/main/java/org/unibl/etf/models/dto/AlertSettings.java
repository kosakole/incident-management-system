package org.unibl.etf.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertSettings {
    @Value("${app.alerts.radius-in-meters}")
    private int radiusInMeters;
    @Value("${app.alerts.incident-threshold}")
    private int incidentThreshold;
    @Value("${app.alerts.time-window-minutes}")
    private int timeWindowMinutes;
}
