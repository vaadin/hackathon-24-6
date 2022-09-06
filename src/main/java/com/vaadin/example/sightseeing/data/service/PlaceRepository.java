package com.vaadin.example.sightseeing.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.example.sightseeing.data.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    public List<Place> findByXBetweenAndYBetween(Double xStart, Double xEnd,
            Double yStart, Double yEnd);
}
