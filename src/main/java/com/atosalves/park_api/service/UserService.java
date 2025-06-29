package com.atosalves.park_api.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.entity.User.Role;
import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.exception.InvalidPasswordException;
import com.atosalves.park_api.exception.UniqueViolationException;
import com.atosalves.park_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public User create(User user) {
                try {
                        var encodedPassword = passwordEncoder.encode(user.getPassword());
                        user.setPassword(encodedPassword);
                        return userRepository.save(user);
                } catch (Exception e) {
                        throw new UniqueViolationException(
                                        String.format("Nome de usuário '%s' já cadastrado", user.getUsername()));
                }
        }

        @Transactional(readOnly = true)
        public User getById(Long id) {
                return userRepository.findById(id).orElseThrow(
                                () -> new EntityNotFoundException(String.format("Usuário '%s' não encontrado", id)));
        }

        @Transactional
        public void updatePassword(Long id, String currentPassword, String updatedPassword, String confirmedPassword) {

                if (!updatedPassword.equals(confirmedPassword)) {
                        throw new InvalidPasswordException("Nova senha não é igual a senha de confirmação");
                }

                var user = getById(id);

                if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                        throw new InvalidPasswordException("Senha não confere");
                }
                var encodedPassword = passwordEncoder.encode(updatedPassword);
                user.setPassword(encodedPassword);
        }

        @Transactional(readOnly = true)
        public List<User> getAll() {
                return userRepository.findAll();
        }

        @Transactional(readOnly = true)
        public User getByUsername(String username) {
                return userRepository.findByUsername(username).orElseThrow(
                                () -> new EntityNotFoundException(
                                                String.format("Usuário '%s' não encontrado", username)));
        }

        @Transactional(readOnly = true)
        public Role getRoleByUsername(String username) {
                return userRepository.findRoleByUsername(username);

        }

}
