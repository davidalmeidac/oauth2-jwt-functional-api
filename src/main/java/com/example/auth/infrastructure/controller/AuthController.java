package com.example.auth.infrastructure.controller;

import com.example.auth.application.AuthService;
import com.example.auth.shared.functional.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para autenticación.
 * Demuestra arquitectura RESTful y programación funcional.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.username(), request.password())
            .getValue()
            .map(token -> ResponseEntity.ok(Map.of("token", token, "type", "Bearer")))
            .orElse(ResponseEntity.status(401)
                .body(Map.of("error", "Credenciales inválidas")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request.username(), request.email(), request.password())
            .getValue()
            .map(user -> ResponseEntity.status(201)
                .body(Map.of("id", user.getId(), "username", user.getUsername())))
            .orElse(ResponseEntity.status(400)
                .body(Map.of("error", "No se pudo registrar el usuario")));
    }

    public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
    ) {}

    public record RegisterRequest(
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String password
    ) {}
}

