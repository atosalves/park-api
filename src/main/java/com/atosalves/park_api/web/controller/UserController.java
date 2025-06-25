package com.atosalves.park_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        public ResponseEntity<User> createUser(@RequestBody User user) {
                var createdUser = userService.createUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
                var user = userService.getUserById(id);
                return ResponseEntity.status(HttpStatus.OK).body(user);
        }

}
