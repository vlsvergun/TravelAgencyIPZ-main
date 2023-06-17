package com.example.travelagencyipz.impl;

import com.example.travelagencyipz.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.travelagencyipz.dao.OrderDao;
import com.example.travelagencyipz.exception.NullEntityReferenceException;
import com.example.travelagencyipz.model.Order;
import com.example.travelagencyipz.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderDao);
    }

    @Test
    void create_NullOrderDto_ThrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> orderService.create(null));

        verify(orderDao, never()).save(any(Order.class));
    }

    @Test
    void readById_ExistingId_ReturnsOrder() {
        Long orderId = 1L;
        Order expectedOrder = new Order();
        expectedOrder.setId(orderId);

        when(orderDao.getById(orderId)).thenReturn(expectedOrder);

        Order result = orderService.readById(orderId);

        assertEquals(expectedOrder, result);
        verify(orderDao, times(1)).getById(orderId);
    }

    @Test
    void readById_NonExistingId_ThrowsNullEntityReferenceException() {
        Long orderId = 1L;

        when(orderDao.getById(orderId)).thenReturn(null);

        assertThrows(NullEntityReferenceException.class, () -> orderService.readById(orderId));

        verify(orderDao, times(1)).getById(orderId);
    }

    @Test
    void readByUserId_ValidId_ReturnsListOfOrders() {
        Long userId = 1L;
        List<Order> expectedOrders = new ArrayList<>();
        // Add some orders to expectedOrders list

        when(orderDao.getAll()).thenReturn(expectedOrders);

        List<Order> result = orderService.readByUserId(userId);

        assertEquals(expectedOrders, result);
        verify(orderDao, times(1)).getAll();
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        List<Order> expectedOrders = new ArrayList<>();
        // Add some orders to expectedOrders list

        when(orderDao.getAll()).thenReturn(expectedOrders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(expectedOrders, result);
        verify(orderDao, times(1)).getAll();
    }


    @Test
    void update_NullOrderDto_ThrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> orderService.update(null));

        verify(orderDao, never()).update(any(Order.class));
    }

    @Test
    void delete_ValidId_DeletesOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderDao.getById(orderId)).thenReturn(order);

        orderService.delete(orderId);

        verify(orderDao, times(1)).getById(orderId);
        verify(orderDao, times(1)).delete(order);
    }

    @Test
    void delete_NonExistingId_ThrowsNullEntityReferenceException() {
        Long orderId = 1L;

        when(orderDao.getById(orderId)).thenReturn(null);

        assertThrows(NullEntityReferenceException.class, () -> orderService.delete(orderId));

        verify(orderDao, times(1)).getById(orderId);
    }

    @Test
    void intervalDays_ValidDates_ReturnsIntervalDays() {
        LocalDate in = LocalDate.now();
        LocalDate out = in.plusDays(5);
        int expectedInterval = 5;

        int result = orderService.intervalDays(in, out);

        assertEquals(expectedInterval, result);
    }

    @Test
    void isValidDates_ValidDates_ReturnsTrue() {
        String in = "2023-06-01";
        String out = "2023-06-06";

        assertTrue(orderService.isValidDates(in, out));
    }

    @Test
    void isValidDates_InvalidDates_ReturnsFalse() {
        String in = "2023-06-10";
        String out = "2023-06-05";

        assertFalse(orderService.isValidDates(in, out));
    }

    @Test
    void getFreeRoomsByHotelAtDates_SomeRoomsAreAvailable_ReturnsListOfRooms() {
        List<Room> reservedRoomsByHotelAtDates = new ArrayList<>();
        reservedRoomsByHotelAtDates.add(new Room());
        // Add some reserved rooms to reservedRoomsByHotelAtDates list

        List<Room> allRoomsByHotel = new ArrayList<>();
        allRoomsByHotel.add(new Room());
        allRoomsByHotel.add(new Room());
        allRoomsByHotel.add(new Room());
        allRoomsByHotel.add(new Room());
        // Add all available rooms to allRoomsByHotel list
        // Include some rooms that are not reserved

        List<Room> expectedRooms = new ArrayList<>();
        // Add the available rooms that are not reserved to expectedRooms list

        List<Room> result = orderService.getFreeRoomsByHotelAtDates(reservedRoomsByHotelAtDates, allRoomsByHotel);

        assertEquals(expectedRooms, result);
    }
}