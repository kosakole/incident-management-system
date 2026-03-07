package org.unibl.etf.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.models.dto.Location;
import org.unibl.etf.models.enums.IncidentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRequest {

    private IncidentType type;
    private Location location;
    private String description;
    private Long userId;
}
