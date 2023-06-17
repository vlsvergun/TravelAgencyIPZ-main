package com.example.travelagencyipz.dao;

import com.example.travelagencyipz.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelDao {

    Hotel create(Hotel hotel);

    Optional<Hotel> readById(Long id);

    List<Hotel> getAllHotels();

    Optional<Hotel> getHotelByName(String name);

    List<Hotel> getHotelsByCountry(String country);

    List<String> getAllCountries();

    Hotel update(Hotel hotel);

    void delete(Long id);
}