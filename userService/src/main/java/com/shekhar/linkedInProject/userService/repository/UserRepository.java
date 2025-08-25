package com.shekhar.linkedInProject.userService.repository;

import com.shekhar.linkedInProject.userService.entiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

   Optional<User> findByEmail(String email);
}
