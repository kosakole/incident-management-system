package org.unibl.etf.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.models.dto.responses.AnalysisResponse;
import org.unibl.etf.services.AnalyticsService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final AnalyticsService analyticsService;

    public AnalysisController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public Mono<ResponseEntity<AnalysisResponse>> getAnalysis(){
        return analyticsService.getAnalysis().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
