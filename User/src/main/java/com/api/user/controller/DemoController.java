package com.api.user.controller;

import com.api.user.authentication.AuthenticationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> demo() {
        String hallo = "Hallo";
        return ResponseEntity.ok(hallo);
    }

}
