package com.example.travelagencyipz.dao;

import com.example.travelagencyipz.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user);

    User getById(Long id);

    List<User> getAll();

    Optional<User> findByEmail(String email);

    User getUserByEmail(String email);

    User update(User user);

    void delete(User user);
}