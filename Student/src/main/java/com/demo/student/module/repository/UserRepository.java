package com.demo.student.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.student.module.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}