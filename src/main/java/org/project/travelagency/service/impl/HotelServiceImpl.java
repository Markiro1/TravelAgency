package org.project.travelagency.service.impl;

import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.exception.HotelNotFoundException;
import org.project.travelagency.exception.SuchHotelExistsException;
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
    public void create(Hotel hotel) {
        if (hotelDao.getAllHotels()
                .stream()
                .anyMatch(h -> hotel.getName().equalsIgnoreCase(h.getName())))
            throw new SuchHotelExistsException();

        hotelDao.create(hotel);
    }

    @Override
    public Hotel readById(Long id) {
        return hotelDao.readById(id).orElseThrow(HotelNotFoundException::new);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelDao.getAllHotels();
    }

    @Override
    public Hotel getHotelByName(String name) {
        return hotelDao.getHotelByName(name).orElseThrow(HotelNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        var hotel = hotelDao.readById(id).orElseThrow(HotelNotFoundException::new);
        hotelDao.delete(hotel.getId());
    }
}