package by.antonpaulavets.authenticationservice.controller;


import by.antonpaulavets.authenticationservice.dto.AuthRequest;
import by.antonpaulavets.authenticationservice.dto.AuthResponse;
import by.antonpaulavets.authenticationservice.dto.RegisterRequest;
import by.antonpaulavets.authenticationservice.dto.TokenValidationResponse;
import by.antonpaulavets.authenticationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validate(@RequestParam String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }
}