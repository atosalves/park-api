package com.atosalves.park_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atosalves.park_api.service.UserService;
import com.atosalves.park_api.web.dto.mapper.UserMapper;
import com.atosalves.park_api.web.dto.user.UserCreateDto;
import com.atosalves.park_api.web.dto.user.UserPasswordDto;
import com.atosalves.park_api.web.dto.user.UserResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

        private final UserService userService;

        @PostMapping
        public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
                var user = userService.create(UserMapper.toUser(createDto));
                return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN') OR (hasRole('CUSTOMER') AND #id == authentication.principal.id)")
        public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
                var user = userService.getById(id);
                return ResponseEntity.ok(UserMapper.toDto(user));
        }

        @PatchMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') AND #id == authentication.principal.id")
        public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                        @Valid @RequestBody UserPasswordDto passwordDto) {
                userService.updatePassword(id, passwordDto.currentPassword(),
                                passwordDto.updatedPassword(), passwordDto.confirmedPassword());
                return ResponseEntity.noContent().build();
        }

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<UserResponseDto>> getAll() {
                var users = userService.getAll();
                return ResponseEntity.ok(UserMapper.toListDto(users));
        }

}
