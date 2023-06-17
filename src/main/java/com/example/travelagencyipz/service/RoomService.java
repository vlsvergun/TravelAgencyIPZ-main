package com.example.travelagencyipz.service;

import com.example.travelagencyipz.model.Room;

import java.util.List;

public interface RoomService {

    void create(Room room);

    Room readById(Long id);

    List<Room> getAllRooms();

    Room getRoomByNumber(int number);

    List<Room> getRoomsByHotelId(Long id);

    Room update(Room room);

    void delete(Long id);
}