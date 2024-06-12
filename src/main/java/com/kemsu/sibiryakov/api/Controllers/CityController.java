package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import com.kemsu.sibiryakov.api.Services.CityService;
import com.kemsu.sibiryakov.api.Services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;
    private final RegionService regionService;

    @Autowired
    public CityController(CityService cityService, RegionService regionService) {
        this.cityService = cityService;
        this.regionService = regionService;
    }

    @GetMapping("/all")
    public List<City> getAll() {
        return cityService.getAll();
    }

    @PostMapping("/add")
    public City addOne(@RequestBody CityAddOneDTO cityAddOneDTO) {
        return cityService.createOneCity(cityAddOneDTO);
    }

    @PostMapping("/addMany")
    public List<City> addMany(@RequestBody CityAddManyDTO cityAddManyDTO) {
        return cityService.createManyCity(cityAddManyDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    private void deleteById(@PathVariable Long id) {
        cityService.deleteOne(id);
    }

    @DeleteMapping("/delete/all")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    public void deleteAll() {
        cityService.deleteAll();
    }
}
