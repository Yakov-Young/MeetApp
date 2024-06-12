package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Region;
import com.kemsu.sibiryakov.api.Repositories.IRegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    private final IRegionRepository repository;

    @Autowired
    public RegionService(IRegionRepository repository) {
        this.repository = repository;
    }

    public List<Region> getAll() {
        return repository.findAll();
    }

    public Region getById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Region crateOneRegion(RegionAddOneDTO regionAddOneDTO) {
        Region region = new Region(regionAddOneDTO.getName());

        return repository.save(region);
    }

    public List<Region> createManyRegion(RegionAddManyDTO regionAddManyDTO) {
        List<Region> regions = new ArrayList<>();

        for (String n : regionAddManyDTO.getName()) {
            regions.add(new Region(n));
        }
        return repository.saveAll(regions);
    }

    public void deleteOne(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
