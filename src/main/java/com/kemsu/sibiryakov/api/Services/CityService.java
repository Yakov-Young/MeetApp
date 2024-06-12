package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Region;
import com.kemsu.sibiryakov.api.Repositories.ICityRepositories;
import com.kemsu.sibiryakov.api.Repositories.IRegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    private final ICityRepositories cityRepositories;
    private final IRegionRepository regionRepository;
    @Autowired
    public CityService(ICityRepositories cityRepositories, IRegionRepository regionRepository) {
        this.cityRepositories = cityRepositories;
        this.regionRepository = regionRepository;
    }

    public List<City> getAll() {
        return cityRepositories.findAll();
    }

    public City getById(Long id) {
        return cityRepositories.findById(id).orElse(null);
    }

    public City createOneCity(CityAddOneDTO cityAddOneDTO) {

        Region region = regionRepository.findById(cityAddOneDTO.getRegionId()).orElseThrow(EntityNotFoundException::new);

        City city = new City(cityAddOneDTO.getName(), region);
        return cityRepositories.save(city);
    }

    public List<City> createManyCity(CityAddManyDTO cityAddManyDTO) {
        List<City> cities = new ArrayList<>();
        Region region = regionRepository.findById(cityAddManyDTO.getRegionId()).orElseThrow(EntityNotFoundException::new);

        for (String n : cityAddManyDTO.getName()) {
            cities.add(new City(n, region));
        }
        return cityRepositories.saveAll(cities);
    }

    public void deleteOne(Long id) {
        cityRepositories.deleteById(id);
    }

    public void deleteAll() {
        cityRepositories.deleteAll();
    }
}
