package com.atosalves.park_api.service;

import org.springframework.stereotype.Service;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

        private UserRepository userRepository;

        @Transactional
        public User createUser(User user) {
                return userRepository.save(user);
        }

}
