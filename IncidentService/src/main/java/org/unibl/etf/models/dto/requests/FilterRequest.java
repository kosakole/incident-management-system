package org.unibl.etf.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.IncidentStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {
    private String type;
    private String city;
    private IncidentStatus status;
    private String date;

    public LocalDateTime getStartDate() {
        if (date != null) {
            LocalDateTime now = LocalDateTime.now();
            return switch (date) {
                case "TODAY" -> now.minusDays(1);
                case "WEAK" -> now.minusWeeks(1);
                case "MONTH" -> now.minusMonths(1);
                case "YEAR" -> now.minusYears(1);
                default -> null;
            };
        }
        return null;
    }
}
