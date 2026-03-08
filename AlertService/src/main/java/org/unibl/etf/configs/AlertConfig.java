package org.unibl.etf.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class AlertConfig {
    @Value("${app.alerts.radius-in-meters}")
    private int radiusInMeters;
    @Value("${app.alerts.incident-threshold}")
    private int incidentThreshold;
    @Value("${app.alerts.time-window-minutes}")
    private int timeWindowMinutes;
}
