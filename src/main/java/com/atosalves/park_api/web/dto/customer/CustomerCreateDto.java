package com.atosalves.park_api.web.dto.customer;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateDto(@NotBlank @Size(min = 3, max = 100) String name,
                @NotBlank @CPF @Size(max = 11) String cpf) {
}