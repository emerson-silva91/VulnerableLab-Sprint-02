package br.unipar.frameworks.dto;

public record LoginResponse(
        String message,
        String token,
        UserResponse user
) {
}