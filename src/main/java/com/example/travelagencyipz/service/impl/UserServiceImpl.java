package com.example.travelagencyipz.service.impl;

import com.example.travelagencyipz.dao.UserDao;
import com.example.travelagencyipz.dto.user.UserCreateDto;
import com.example.travelagencyipz.dto.user.UserUpdateDto;
import com.example.travelagencyipz.exception.NullEntityReferenceException;
import com.example.travelagencyipz.mapper.UserCreateMapper;
import com.example.travelagencyipz.mapper.UserUpdateMapper;
import com.example.travelagencyipz.model.User;
import com.example.travelagencyipz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserCreateDto userDto) {
        if (userDto != null) {
            User user = UserCreateMapper.mapToModel(userDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public User readById(Long id) {
        User user = userDao.getById(id);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }


    @Override
    public User update(UserUpdateDto userDto) {
        String password;
        if (userDto != null) {
            User user = UserUpdateMapper.mapToModel(userDto);
            if (!user.getPassword().isBlank()) {
                password = passwordEncoder.encode(user.getPassword());
            } else {
                password = readById(user.getId()).getPassword();
            }
            user.setPassword(password);
            return userDao.update(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public void delete(Long id) {
        User user = readById(id);
        userDao.delete(user);
    }
}