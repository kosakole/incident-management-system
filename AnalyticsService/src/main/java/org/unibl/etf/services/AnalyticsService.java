package org.unibl.etf.services;

import org.unibl.etf.models.dto.responses.AnalysisResponse;
import reactor.core.publisher.Mono;

public interface AnalyticsService {

    Mono<AnalysisResponse> getAnalysis();
}
