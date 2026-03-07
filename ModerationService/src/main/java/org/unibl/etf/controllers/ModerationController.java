package org.unibl.etf.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.ModerationService;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/moderation")
public class ModerationController {

    private final ModerationService moderationService;

    public ModerationController(ModerationService moderationService) {
        this.moderationService = moderationService;
    }

    @GetMapping(value = "/new-incidents")
    public Mono<ResponseEntity<List<IncidentResponse>>> getNewIncidents(Authentication auth){
        return moderationService.getNewIncidents().map(ResponseEntity::ok);
    }

    @PatchMapping(value = "/{id}/accept")
    public Mono<ResponseEntity<Void>> acceptIncident(@PathVariable(value = "id") Long id){
        return moderationService.accept(id).thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(value = "/{id}/reject")
    public Mono<ResponseEntity<Void>> rejectIncident(@PathVariable(value = "id") Long id){
        return moderationService.reject(id).thenReturn(ResponseEntity.noContent().build());
    }
}
