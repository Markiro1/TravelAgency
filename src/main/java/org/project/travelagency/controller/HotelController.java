package org.project.travelagency.controller;

import org.project.travelagency.model.Hotel;
import org.project.travelagency.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> addHotel(@RequestBody Hotel hotel) {
        hotelService.create(hotel);
        return null;
    }
}