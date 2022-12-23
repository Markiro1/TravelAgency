package org.project.travelagency.controller;

import org.project.travelagency.dao.impl.HotelDaoImpl;
import org.project.travelagency.model.Hotel;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        var newHotel = hotelService.create(hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    public static void main(String[] args) {
        HotelDaoImpl hotelDao = new HotelDaoImpl();
        HotelServiceImpl hotelService = new HotelServiceImpl(hotelDao);
        Hotel hotel = new Hotel();
        hotel.setName("Name");
        hotel.setCountry("Country");
        hotel.setCity("City");

        hotelService.create(hotel);
    }
}