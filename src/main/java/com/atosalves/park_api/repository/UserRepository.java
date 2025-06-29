package com.atosalves.park_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atosalves.park_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);

        @Query("SELECT u.role FROM User u WHERE u.username LIKE :username")
        User.Role findRoleByUsername(String username);

}
