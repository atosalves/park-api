package com.atosalves.park_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

        private final UserRepository userRepository;

        @Transactional
        public User createUser(User user) {
                return userRepository.save(user);
        }

        @Transactional(readOnly = true)
        public User getUserById(Long id) {
                return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        }

}
