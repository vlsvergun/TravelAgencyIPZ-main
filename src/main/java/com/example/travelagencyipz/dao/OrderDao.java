package com.example.travelagencyipz.dao;

import com.example.travelagencyipz.model.Order;

import java.util.List;

public interface OrderDao {

    Order save(Order order);

    Order getById(Long id);

    List<Order> getAll();

    Order update(Order order);

    void delete(Order order);
}