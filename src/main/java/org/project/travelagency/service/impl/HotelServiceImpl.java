package org.project.travelagency.service.impl;

import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.model.Hotel;
import org.project.travelagency.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelDao hotelDao;

    @Autowired
    public HotelServiceImpl(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    @Override
    public Hotel create(Hotel hotel) {
        return hotelDao.create(hotel);
    }

    @Override
    public Hotel readById(Long id) {
        return hotelDao.readById(id);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return null;
    }

    @Override
    public Hotel getHotelByName(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}