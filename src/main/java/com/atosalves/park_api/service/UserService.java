package com.atosalves.park_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.exception.InvalidPasswordException;
import com.atosalves.park_api.exception.UniqueViolationException;
import com.atosalves.park_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

        private final UserRepository userRepository;

        @Transactional
        public User create(User user) {
                try {
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

                if (!user.getPassword().equals(currentPassword)) {
                        throw new InvalidPasswordException("Senha não confere");
                }

                user.setPassword(updatedPassword);
        }

        @Transactional(readOnly = true)
        public List<User> getAll() {
                return userRepository.findAll();
        }

}
