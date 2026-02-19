package com.example.SpringBootEcommerceBackend.infrastructure.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootEcommerceBackend.domain.user.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
} 
