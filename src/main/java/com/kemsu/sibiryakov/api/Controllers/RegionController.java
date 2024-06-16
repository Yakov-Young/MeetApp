package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.RegionDTO.RegionAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Region;
import com.kemsu.sibiryakov.api.Services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

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
    public ResponseEntity<List<Region>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<Region> regions = regionService.getAll();

            return !regions.isEmpty()
                    ? new ResponseEntity<>(regions, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Region> addOne(@RequestBody RegionAddOneDTO regionAddOneDTO,
                                         @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            Region region = regionService.crateOneRegion(regionAddOneDTO);

            return region != null
                    ? new ResponseEntity<>(region, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/addMany")
    public ResponseEntity<List<Region>> addMany(@RequestBody RegionAddManyDTO regionAddManyDTO,
                                                @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<Region> regions = regionService.createManyRegion(regionAddManyDTO);

            return regions != null
                    ? new ResponseEntity<>(regions, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    private ResponseEntity<?> deleteById(@PathVariable Long id,
                                         @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            regionService.deleteOne(id);

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/delete/all")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    public ResponseEntity<?> deleteAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            regionService.deleteAll();

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }
}
