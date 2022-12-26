package org.project.travelagency.service;

import org.project.travelagency.model.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    Hotel readById(Long id);

    List<Hotel> getAllHotels();

    Hotel getHotelByName(String name);

    List<Hotel> getHotelsByCountry(String country);

    List<String> getAllCountries();

    Hotel update(Hotel hotel);

    void delete(Long id);
}