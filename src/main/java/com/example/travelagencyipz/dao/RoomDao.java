package com.example.travelagencyipz.dao;

import com.example.travelagencyipz.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {

    void create(Room room);

    Optional<Room> readById(Long id);

    List<Room> getAllRooms();

    Optional<Room> getRoomByNumber(int number);

    List<Room> getRoomsByHotelId(Long id);

    Room update(Room room);

    void delete(Long id);
}