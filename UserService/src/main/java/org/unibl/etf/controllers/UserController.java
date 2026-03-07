package org.unibl.etf.controllers;

import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.models.dto.responses.UserResponse;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.repositories.UserRepository;
import org.unibl.etf.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

//    @PatchMapping(value = "/{id}/role")
//    public ResponseEntity<Void> changeRole(@PathVariable(value = "id") Long id, @RequestBody UserRole role) {
//        userService.changeRole(id, role);
//        return ResponseEntity.noContent().build();
//    }

    @PatchMapping(value = "/{id}/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> changeRole(@PathVariable(value = "id") Long id) {
        userService.changeRole(id);
        return ResponseEntity.noContent().build();
    }
}
