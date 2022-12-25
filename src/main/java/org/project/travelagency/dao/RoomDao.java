package org.project.travelagency.dao;

import org.project.travelagency.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {

    void create(Room room);

    Optional<Room> readById(Long id);

    List<Room> getAllRooms();

    Optional<Room> getRoomByNumber(int number);

    Room update(Room room);

    void delete(Long id);
}