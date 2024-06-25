package br.com.sistema_cadastros.inicio.security.auth_dto;

import br.com.sistema_cadastros.inicio.security.user_security.UserRole;

public record RegisterDTO(
    String login,
    String password,
    UserRole role
) {
    
}
