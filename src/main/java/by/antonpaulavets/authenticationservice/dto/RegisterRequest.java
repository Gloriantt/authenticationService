package by.antonpaulavets.authenticationservice.dto;

import by.antonpaulavets.authenticationservice.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}