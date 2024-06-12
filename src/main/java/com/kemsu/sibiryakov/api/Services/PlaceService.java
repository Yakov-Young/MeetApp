package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.PlaceDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Repositories.ICityRepositories;
import com.kemsu.sibiryakov.api.Repositories.IPlacesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private final IPlacesRepository placesRepository;
    private final ICityRepositories cityRepositories;

    @Autowired
    public PlaceService(IPlacesRepository placesRepository, ICityRepositories cityRepositories) {
        this.placesRepository = placesRepository;
        this.cityRepositories = cityRepositories;
    }
    public List<Place> getAll() {
        return placesRepository.findAll();
    }
    public Place getById(Long id) {
        return placesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public Place create(PlaceDTO placeDTO) {
        Place place = new Place(
                cityRepositories.findById(placeDTO.getCity_id()).orElseThrow(EntityNotFoundException::new),
                placeDTO.getStreet(),
                placeDTO.getNumber(),
                placeDTO.getApartment());
        return placesRepository.save(place);
    }
}
