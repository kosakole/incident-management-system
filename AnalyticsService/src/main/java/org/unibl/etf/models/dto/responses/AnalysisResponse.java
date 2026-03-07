package org.unibl.etf.models.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.IncidentType;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResponse {
    private Map<String, Long> analysisByCity;
    private Map<IncidentType, Long> analysisByType;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Map<LocalDate, Long> analysisByDate;
}
