package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.Services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/place")
public class PlaceController {
    public final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Place> getAll() {
//        return placeService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Place> getById(@PathVariable Long id) {
//        Place place = placeService.getById(id);
//
//        return place != null
//                ? new ResponseEntity<>(place, HttpStatusCode.valueOf(200))
//                : new ResponseEntity<>(HttpStatusCode.valueOf(404));
//    }
//
//    @PostMapping("/add")
//    @ResponseStatus(value = HttpStatus.CREATED, reason = "ok")
//    public ResponseEntity<Place> createOnePlace(@RequestBody PlaceDTO placeDTO) {
//        Place place = placeService.create(placeDTO);
//
//        return place != null
//                ? new ResponseEntity<>(place, HttpStatusCode.valueOf(201))
//                : new ResponseEntity<>(HttpStatusCode.valueOf(400));
//    }
}
