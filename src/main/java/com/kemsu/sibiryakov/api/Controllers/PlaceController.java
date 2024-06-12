package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.PlaceDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/place")
public class PlaceController {
    public final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/all")
    public List<Place> getAll() {
        return placeService.getAll();
    }

    public Place getById(Long id) {
        return placeService.getById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "ok")
    public Place createOnePlace(@RequestBody PlaceDTO placeDTO) {
        return placeService.create(placeDTO);
    }
}
