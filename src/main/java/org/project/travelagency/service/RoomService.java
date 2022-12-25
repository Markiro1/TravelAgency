package org.project.travelagency.service;

import org.project.travelagency.model.Room;

import java.util.List;

public interface RoomService {

    void create(Room room);

    Room readById(Long id);

    List<Room> getAllRooms();

    Room getRoomByNumber(int number);

    Room update(Room room);

    void delete(Long id);
}