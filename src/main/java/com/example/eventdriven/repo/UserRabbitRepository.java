package com.example.eventdriven.repo;

import com.example.eventdriven.model.UserRabbit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRabbitRepository extends JpaRepository<UserRabbit, Long> {
    Optional<UserRabbit> findByEmail(String email);
}
