package com.example.travelagencyipz.impl;

import com.example.travelagencyipz.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.travelagencyipz.dao.HotelDao;
import com.example.travelagencyipz.exception.HotelNotFoundException;
import com.example.travelagencyipz.exception.SuchHotelExistsException;
import com.example.travelagencyipz.model.Country;
import com.example.travelagencyipz.model.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {
    @Mock
    private HotelDao hotelDao;

    private HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotelService = new HotelServiceImpl(hotelDao);
    }

    @Test
    void create_ValidHotel_Success() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel A");

        when(hotelDao.getAllHotels()).thenReturn(new ArrayList<>());
        when(hotelDao.create(hotel)).thenReturn(hotel);

        Hotel createdHotel = hotelService.create(hotel);

        assertEquals(hotel, createdHotel);
        verify(hotelDao, times(1)).getAllHotels();
        verify(hotelDao, times(1)).create(hotel);
    }

    @Test
    void create_DuplicateHotel_ThrowsSuchHotelExistsException() {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel A");

        List<Hotel> existingHotels = new ArrayList<>();
        existingHotels.add(new Hotel(1L, "Hotel B", Country.EGYPT));
        existingHotels.add(new Hotel(2L, "Hotel A", Country.ARGENTINA));

        when(hotelDao.getAllHotels()).thenReturn(existingHotels);

        assertThrows(SuchHotelExistsException.class, () -> hotelService.create(hotel));

        verify(hotelDao, times(1)).getAllHotels();
        verify(hotelDao, never()).create(hotel);
    }

    @Test
    void readById_ExistingId_ReturnsHotel() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel(hotelId, "Hotel A", Country.ARGENTINA);

        when(hotelDao.readById(hotelId)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.readById(hotelId);

        assertEquals(hotel, result);
        verify(hotelDao, times(1)).readById(hotelId);
    }

    @Test
    void readById_NonExistingId_ThrowsHotelNotFoundException() {
        Long hotelId = 1L;

        when(hotelDao.readById(hotelId)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.readById(hotelId));

        verify(hotelDao, times(1)).readById(hotelId);
    }
}