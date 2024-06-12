package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepositories extends JpaRepository<City, Long> {
}
