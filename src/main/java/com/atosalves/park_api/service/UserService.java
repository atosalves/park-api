package com.atosalves.park_api.service;

import java.util.List;

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
        public User create(User user) {
                return userRepository.save(user);
        }

        @Transactional(readOnly = true)
        public User getById(Long id) {
                return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        }

        @Transactional
        public User updatePassword(Long id, String password) {
                var user = getById(id);

                user.setPassword(password);

                return user;
        }

        @Transactional(readOnly = true)
        public List<User> getAll() {
                return userRepository.findAll();
        }

}
