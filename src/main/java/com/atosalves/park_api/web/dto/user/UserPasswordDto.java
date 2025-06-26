package com.atosalves.park_api.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordDto(
                @NotBlank(message = "A senha não deve estar vazia") @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres") String currentPassword,
                @NotBlank(message = "A senha não deve estar vazia") @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres") String updatedPassword,
                @NotBlank(message = "A senha não deve estar vazia") @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres") String confirmedPassword) {

}
