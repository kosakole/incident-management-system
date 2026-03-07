package org.unibl.etf.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.IncidentStatus;
import org.unibl.etf.models.enums.IncidentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private IncidentType type;
    private Double latitude;
    private Double longitude;
    @Enumerated(EnumType.STRING)
    private IncidentStatus status;
    private String description;
    private LocalDateTime reportedAt;
    private String image;
    private Long userId;
}
