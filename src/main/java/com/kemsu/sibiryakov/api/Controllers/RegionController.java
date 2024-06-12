package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Region;
import com.kemsu.sibiryakov.api.Services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {
    private final RegionService regionService;
    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/all")
    public List<Region> getAll() {
        return regionService.getAll();
    }

    @PostMapping("/add")
    public Region addOne(@RequestBody RegionAddOneDTO regionAddOneDTO) {
        return regionService.crateOneRegion(regionAddOneDTO);
    }

    @PostMapping("/addMany")
    public List<Region> addMany(@RequestBody RegionAddManyDTO regionAddManyDTO) {
        return regionService.createManyRegion(regionAddManyDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    private void deleteById(@PathVariable Long id) {
        regionService.deleteOne(id);
    }

    @DeleteMapping("/delete/all")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    public void deleteAll() {
        regionService.deleteAll();
    }
}
