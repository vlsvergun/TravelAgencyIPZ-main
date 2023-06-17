package com.example.travelagencyipz.service;

import com.example.travelagencyipz.dto.order.OrderCreateDto;
import com.example.travelagencyipz.dto.order.OrderUpdateDto;
import com.example.travelagencyipz.model.Order;
import com.example.travelagencyipz.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Order create(OrderCreateDto orderDto);

    Order readById(Long id);

    List<Order> readByUserId(Long id);

    List<Order> getAllOrders();

    Order update(OrderUpdateDto orderDto);

    void delete(Long id);

    int intervalDays(LocalDate in, LocalDate out);

    Boolean isValidDates(String in, String out);

    List<Order> getOrdersByHotelAtDates(List<Order> orders, OrderCreateDto orderDto, String dayIn, String dayOut);

    List<Room> getReservedRoomsByHotelAtDates(List<Order> ordersByHotelAtDates);

    List<Room> getFreeRoomsByHotelAtDates(List<Room> reservedRoomsByHotelAtDates, List<Room> allRoomsByHotel);
}