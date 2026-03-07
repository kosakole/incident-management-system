package org.unibl.etf.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.dto.Location;
import org.unibl.etf.models.enums.IncidentType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentResponse {
    private long id;
    private IncidentType type;
    private Location location;
    private LocalDateTime reportedAt;
}
