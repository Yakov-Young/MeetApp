package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddManyDTO;
import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddOneDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import com.kemsu.sibiryakov.api.Services.CityService;
import com.kemsu.sibiryakov.api.Services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kemsu.sibiryakov.api.Services.RightsService.checkRight;

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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<City>> getAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<City> cities = cityService.getAll();

            return !cities.isEmpty()
                    ? new ResponseEntity<>(cities, HttpStatusCode.valueOf(200))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(404));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<City> addOne(@RequestBody CityAddOneDTO cityAddOneDTO,
                                       @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            City city = cityService.createOneCity(cityAddOneDTO);

            return city != null
                    ? new ResponseEntity<>(city, HttpStatusCode.valueOf(201))
                    : new ResponseEntity<>(HttpStatusCode.valueOf(400));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @PostMapping("/addMany")
    public ResponseEntity<List<City>> addMany(@RequestBody CityAddManyDTO cityAddManyDTO,
                                              @CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            List<City> cities = cityService.createManyCity(cityAddManyDTO);

            return cities != null
                    ? new ResponseEntity<>(cities, HttpStatusCode.valueOf(201))
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
            cityService.deleteOne(id);

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    @DeleteMapping("/delete/all")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "ok")
    public ResponseEntity<?> deleteAll(@CookieValue(value = "jwt", required = false) String jwt) {
        if (checkRight(jwt, ERole.ADMINISTRATOR)) {
            cityService.deleteAll();

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }
}
