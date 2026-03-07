package org.unibl.etf.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.services.UserService;

@RestController
@RequestMapping(value = "/api/users/oauth")
public class UserOAuthController {

    private final UserService userService;

    public UserOAuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<UserOAuth2Response> handleOAuthUser(@RequestBody UserOAuth2Request request) {
        return ResponseEntity.ok(userService.getUserOAuth2(request));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRole(@AuthenticationPrincipal Jwt jwt){
        if(jwt == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(jwt.getClaim("roles"));
    }

}
