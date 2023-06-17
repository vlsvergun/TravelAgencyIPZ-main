package com.example.travelagencyipz.service;

import com.example.travelagencyipz.dto.user.UserCreateDto;
import com.example.travelagencyipz.dto.user.UserUpdateDto;
import com.example.travelagencyipz.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(UserCreateDto userDto);

    User readById(Long id);

    List<User> getAllUsers();

    Optional<User> findUserByEmail(String email);

    User getUserByEmail(String email);

    User update(UserUpdateDto userDto);

    void delete(Long id);
}