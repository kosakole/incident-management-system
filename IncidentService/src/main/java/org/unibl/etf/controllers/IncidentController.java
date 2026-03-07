package org.unibl.etf.controllers;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.unibl.etf.models.dto.requests.FilterRequest;
import org.unibl.etf.models.dto.requests.IncidentRequest;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.IncidentBroadcaster;
import org.unibl.etf.services.IncidentService;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentBroadcaster broadcaster;

    public IncidentController(IncidentService incidentService, IncidentBroadcaster broadcaster) {
        this.incidentService = incidentService;
        this.broadcaster = broadcaster;
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<IncidentResponse>> getAllWithFilter(@ModelAttribute FilterRequest request) {
        return ResponseEntity.ok(incidentService.getAcceptedIncidents(request));
    }

    @GetMapping
    public ResponseEntity<List<IncidentResponse>> getAll() {
        return ResponseEntity.ok(incidentService.getAcceptedIncidents());
    }

    @GetMapping(value = "/new")
    public ResponseEntity<List<IncidentResponse>> getNewIncidents() {
        return ResponseEntity.ok(incidentService.getNewIncidents());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IncidentResponse> get(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(incidentService.get(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<Void> create(@RequestPart("incident") IncidentRequest incident, @RequestPart(value = "image", required = false) MultipartFile image, @AuthenticationPrincipal Jwt jwt) {
        incident.setUserId(jwt.getClaim("userId"));
        IncidentResponse incidentResponse = incidentService.create(incident, image);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(incidentResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/{id}/accept")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> confirmIncident(@PathVariable(value = "id") Long id) {
        incidentService.accept(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/reject")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> rejectIncident(@PathVariable(value = "id") Long id) {
        incidentService.reject(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<IncidentResponse>> stream() {
        return ResponseEntity.ok()
                .header("X-Accel-Buffering", "no")
                .header("Cache-Control", "no-cache")
                .body(broadcaster.getIncidentStream());
    }
}
