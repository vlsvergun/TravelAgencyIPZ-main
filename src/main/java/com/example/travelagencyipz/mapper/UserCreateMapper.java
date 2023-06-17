package com.example.travelagencyipz.mapper;

import com.example.travelagencyipz.dto.user.UserCreateDto;
import com.example.travelagencyipz.model.User;

public class UserCreateMapper {

    public static User mapToModel(UserCreateDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstname())
                .lastName(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}