package by.antonpaulavets.authenticationservice.service;

import by.antonpaulavets.authenticationservice.config.JwtService;
import by.antonpaulavets.authenticationservice.model.*;
import by.antonpaulavets.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = UserEntity.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();

        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }

    public AuthResponse authenticate(AuthRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new AuthResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }

    public TokenValidationResponse validateToken(String token) {
        return new TokenValidationResponse(jwtService.isTokenValid(token));
    }
}
