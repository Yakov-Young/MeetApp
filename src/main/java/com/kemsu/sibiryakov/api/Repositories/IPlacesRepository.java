package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlacesRepository extends JpaRepository<Place, Long> {
    List<Place> findAll();
}

