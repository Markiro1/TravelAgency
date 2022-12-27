package org.project.travelagency.service.impl;

import org.project.travelagency.dao.RoomDao;

import org.project.travelagency.exception.RoomNotFoundException;
import org.project.travelagency.exception.SuchRoomExistsException;
import org.project.travelagency.model.Room;
import org.project.travelagency.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public void create(Room room) {
        if (roomDao.getAllRooms()
                .stream()
                .anyMatch(r -> room.getNumber() == r.getNumber()))
            throw new SuchRoomExistsException();

        roomDao.create(room);
    }

    @Override
    public Room readById(Long id) {
        return roomDao.readById(id).orElseThrow(RoomNotFoundException::new);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomDao.getAllRooms();
    }

    @Override
    public Room getRoomByNumber(int number) {
        return roomDao.getRoomByNumber(number).orElseThrow(RoomNotFoundException::new);
    }


    @Override
    public List<Room> getRoomsByHotelId(Long id) {
        return roomDao.getRoomsByHotelId(id);
    }

    @Override
    public Room update(Room room) {
        return roomDao.update(room);
    }

    @Override
    public void delete(Long id) {
        var room = roomDao.readById(id).orElseThrow(RoomNotFoundException::new);
        roomDao.delete(room.getId());
    }
}