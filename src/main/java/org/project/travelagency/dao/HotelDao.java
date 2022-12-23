package org.project.travelagency.dao;


import org.project.travelagency.model.Hotel;

import java.util.List;

public interface HotelDao {

    Hotel create(Hotel hotel);

    Hotel readById(Long id);

    List<Hotel> getAllHotels();

    Hotel getHotelByName(String name);

    void delete(Long id);
}