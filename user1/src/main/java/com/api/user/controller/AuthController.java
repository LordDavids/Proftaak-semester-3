package com.api.user.controller;

import com.api.user.dto.AuthenticationRequestDTO;
import com.api.user.dto.AuthenticationResponseDTO;
import com.api.user.entities.Auth;
import com.api.user.service.AuthenticationService;
import com.api.user.dto.RegisterRequestDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Auth> register(
        @RequestBody RegisterRequestDTO request
    ){
        AuthenticationResponseDTO response = authenticationService.register(request);
        ResponseCookie cookie = ResponseCookie.from("JWT", response.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new Auth(response));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Auth> authentication(
        @RequestBody AuthenticationRequestDTO request
    ){
        AuthenticationResponseDTO response = authenticationService.authenticate(request);
            ResponseCookie cookie = ResponseCookie.from("JWT", response.getToken())
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * 60)
                        .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new Auth(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }
}
