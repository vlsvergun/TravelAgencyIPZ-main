package com.example.travelagencyipz.mapper;

import com.example.travelagencyipz.dto.user.UserUpdateDto;
import com.example.travelagencyipz.model.User;

public class UserUpdateMapper {

    public static User mapToModel(UserUpdateDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstname())
                .lastName(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .orders(userDto.getOrders())
                .build();
    }

    public static UserUpdateDto mapToDto(User user) {
        UserUpdateDto userDto = new UserUpdateDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastname(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setOrders(user.getOrders());
        return userDto;
    }
}