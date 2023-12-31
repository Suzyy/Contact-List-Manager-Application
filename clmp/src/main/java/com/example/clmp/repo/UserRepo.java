package com.example.clmp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clmp.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}
