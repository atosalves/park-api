package com.atosalves.park_api.web.controller.dto.user;

public record UserPasswordDto(String currentPassword, String updatedPassword, String confirmedPassword) {

}
