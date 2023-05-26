package com.vaadin.example.sightseeing.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.example.sightseeing.data.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    public List<Place> findByXBetweenAndYBetween(Double xStart, Double xEnd,
            Double yStart, Double yEnd);
}
