package com.atosalves.park_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

        private final UserService userService;

        @PostMapping
        public ResponseEntity<User> create(@RequestBody User user) {
                var createdUser = userService.create(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getById(@PathVariable Long id) {
                var user = userService.getById(id);
                return ResponseEntity.ok(user);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
                var updatedUser = userService.updatePassword(id, user.getPassword());
                return ResponseEntity.ok(updatedUser);
        }

}
