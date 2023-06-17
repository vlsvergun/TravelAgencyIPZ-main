package com.example.travelagencyipz.service.impl;

import com.example.travelagencyipz.dao.HotelDao;
import com.example.travelagencyipz.exception.HotelNotFoundException;
import com.example.travelagencyipz.exception.SuchHotelExistsException;
import com.example.travelagencyipz.model.Hotel;
import com.example.travelagencyipz.service.HotelService;
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
        if (hotelDao.getAllHotels()
                .stream()
                .anyMatch(h -> hotel.getName().equalsIgnoreCase(h.getName())))
            throw new SuchHotelExistsException();

        return hotelDao.create(hotel);
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
    public List<Hotel> getHotelsByCountry(String country) {
        return hotelDao.getHotelsByCountry(country);
    }

    @Override
    public List<String> getAllCountries() {
        return hotelDao.getAllCountries();
    }

    @Override
    public Hotel update(Hotel hotel) {
        return hotelDao.update(hotel);
    }

    @Override
    public void delete(Long id) {
        var hotel = hotelDao.readById(id).orElseThrow(HotelNotFoundException::new);
        hotelDao.delete(hotel.getId());
    }
}