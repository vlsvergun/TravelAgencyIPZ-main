package com.example.travelagencyipz.service.impl;

import com.example.travelagencyipz.dao.OrderDao;
import com.example.travelagencyipz.dto.order.OrderCreateDto;
import com.example.travelagencyipz.dto.order.OrderUpdateDto;
import com.example.travelagencyipz.exception.NullEntityReferenceException;
import com.example.travelagencyipz.mapper.OrderCreateMapper;
import com.example.travelagencyipz.mapper.OrderUpdateMapper;
import com.example.travelagencyipz.model.Order;
import com.example.travelagencyipz.model.Room;
import com.example.travelagencyipz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order create(OrderCreateDto orderDto) {
        if (orderDto != null) {
            Order order = OrderCreateMapper.mapToModel(orderDto);
            return orderDao.save(order);
        } else {
            throw new NullEntityReferenceException("Order cannot be 'null'");
        }
    }

    @Override
    public Order readById(Long id) {
        Order order = orderDao.getById(id);
        if (order != null) {
            return order;
        } else {
            throw new NullEntityReferenceException("Order not found");
        }
    }

    @Override
    public List<Order> readByUserId(Long id) {
        return orderDao.getAll().stream().filter(o -> o.getUser().getId() == id).collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

    @Override
    public Order update(OrderUpdateDto orderDto) {
        if (orderDto != null) {
            Order order = OrderUpdateMapper.mapToModel(orderDto);
            return orderDao.update(order);
        } else {
            throw new NullEntityReferenceException("Order cannot be 'null'");
        }
    }

    @Override
    public void delete(Long id) {
        Order order = readById(id);
        orderDao.delete(order);
    }

    @Override
    public int intervalDays(LocalDate in, LocalDate out) {
        return (int) in.until(out, ChronoUnit.DAYS);
    }

    @Override
    public Boolean isValidDates(String in, String out) {
        if (in.isBlank() || out.isBlank()) return false;
        return intervalDays(LocalDate.parse(in), LocalDate.parse(out)) >= 0;
    }

    @Override
    public List<Order> getOrdersByHotelAtDates(List<Order> orders,
                                               OrderCreateDto orderDto,
                                               String dayIn,
                                               String dayOut) {
        return orders.stream()
                .filter(order -> order.getHotel().getName().equals(orderDto.getHotel()))
                .filter(order ->
                        (isValidDates(order.getCheckOut().toString(), dayOut)
                                && isValidDates(dayIn, order.getCheckOut().toString()))
                                || (isValidDates(order.getCheckIn().toString(), dayOut)
                                && isValidDates(dayIn, order.getCheckIn().toString()))
                )
                .toList();
    }

    @Override
    public List<Room> getReservedRoomsByHotelAtDates(List<Order> ordersByHotelAtDates) {
        return ordersByHotelAtDates.stream()
                .map(Order::getReservedRooms)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    @Override
    public List<Room> getFreeRoomsByHotelAtDates(List<Room> reservedRoomsByHotelAtDates,
                                                 List<Room> allRoomsByHotel) {
        return allRoomsByHotel.size() == reservedRoomsByHotelAtDates.size()
                ? null
                : allRoomsByHotel.stream()
                .filter(r -> !reservedRoomsByHotelAtDates.contains(r))
                .toList();
    }
}