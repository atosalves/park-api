package com.atosalves.park_api.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
                @NotBlank(message = "O nome de usuário não deve estar vazio") String username,
                @NotBlank(message = "A senha não deve estar vazia") @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres") String password) {
}