package com.vaadin.example.sightseeing.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.example.sightseeing.data.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
