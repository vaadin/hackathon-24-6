package com.vaadin.example.sightseeing.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.example.sightseeing.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
