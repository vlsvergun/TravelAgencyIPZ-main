package com.example.travelagencyipz.mapper;

import com.example.travelagencyipz.dao.impl.HotelDaoImpl;
import com.example.travelagencyipz.dto.order.OrderCreateDto;
import com.example.travelagencyipz.model.Order;
import com.example.travelagencyipz.service.HotelService;
import com.example.travelagencyipz.service.impl.HotelServiceImpl;

import java.time.LocalDate;

public class OrderCreateMapper {

    public static Order mapToModel(OrderCreateDto orderDto) {
        HotelService hotelService = new HotelServiceImpl(new HotelDaoImpl());
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .checkIn(LocalDate.parse(orderDto.getCheckIn()))
                .checkOut(LocalDate.parse(orderDto.getCheckOut()))
                .user(orderDto.getUser())
                .hotel(hotelService.getHotelByName(orderDto.getHotel()))
                .reservedRooms(orderDto.getRooms())
                .amount(orderDto.getAmount())
                .build();
    }
}