package com.example.travelagencyipz.dto.order;

import lombok.*;
import com.example.travelagencyipz.model.Country;
import com.example.travelagencyipz.model.Room;
import com.example.travelagencyipz.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderCreateDto {
    private LocalDateTime orderDate;
    private String checkIn;
    private String checkOut;
    private Country country;
    private String hotel;
    private User user;
    private List<Room> rooms = new ArrayList<>();
    private int reservedRoomsCount;
    private double amount;
}