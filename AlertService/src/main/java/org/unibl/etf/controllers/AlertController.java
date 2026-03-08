package org.unibl.etf.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.configs.AlertConfig;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.AlertService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PutMapping(value = "/settings")
    public ResponseEntity<Void> updateSettings(@RequestBody AlertConfig newSettings) {
        alertService.updateSettings(newSettings);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IncidentResponse> streamAlerts() {
        return alertService.getAlertStream();
    }
}