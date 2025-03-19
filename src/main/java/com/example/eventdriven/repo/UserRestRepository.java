package com.example.eventdriven.repo;

import com.example.eventdriven.repo.entities.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRestRepository extends JpaRepository<UserRest, Long> {
    Optional<UserRest> findByEmail(String email);
}
