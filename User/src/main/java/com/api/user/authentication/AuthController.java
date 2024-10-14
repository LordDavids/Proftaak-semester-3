package com.api.user.authentication;

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
    public ResponseEntity<AuthenticationResponseDTO> register(
        @RequestBody RegisterRequestDTO request
    )
    {
        String token = authenticationService.register(request).token();
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authentication(
        @RequestBody AuthenticationRequestDTO request
    ){
        String token = authenticationService.authenticate(request).token();
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * 60)
                        .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();

    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
