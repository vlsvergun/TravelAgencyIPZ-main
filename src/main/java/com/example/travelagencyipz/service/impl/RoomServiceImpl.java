package com.example.travelagencyipz.service.impl;

import com.example.travelagencyipz.dao.RoomDao;
import com.example.travelagencyipz.exception.RoomNotFoundException;
import com.example.travelagencyipz.exception.SuchRoomExistsException;
import com.example.travelagencyipz.model.Room;
import com.example.travelagencyipz.service.RoomService;
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
                .anyMatch(room::equals))
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