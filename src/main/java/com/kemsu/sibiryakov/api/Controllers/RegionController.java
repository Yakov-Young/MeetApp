package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Region;
import com.kemsu.sibiryakov.api.Services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Region> getAll() {
        return regionService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Region> addOne(@RequestBody RegionAddOneDTO regionAddOneDTO) {
        Region region = regionService.crateOneRegion(regionAddOneDTO);

        return region != null
                ? new ResponseEntity<>(region, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @PostMapping("/addMany")
    public ResponseEntity<List<Region>> addMany(@RequestBody RegionAddManyDTO regionAddManyDTO) {
        List<Region> regions = regionService.createManyRegion(regionAddManyDTO);

        return regions != null
                ? new ResponseEntity<>(regions, HttpStatusCode.valueOf(201))
                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
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
