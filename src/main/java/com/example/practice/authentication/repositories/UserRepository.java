package com.example.practice.authentication.repositories;

import com.example.practice.authentication.model.entities.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDao, UUID> {
    Optional<UserDao> findByEmail(String email);
}
