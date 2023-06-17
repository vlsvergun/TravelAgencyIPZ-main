package com.example.travelagencyipz.impl;

import com.example.travelagencyipz.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.travelagencyipz.dao.UserDao;
import com.example.travelagencyipz.exception.NullEntityReferenceException;
import com.example.travelagencyipz.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao, passwordEncoder);
    }


    @Test
    void create_NullUserDto_ThrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> userService.create(null));

        verify(passwordEncoder, never()).encode(anyString());
        verify(userDao, never()).save(any(User.class));
    }

    @Test
    void readById_ValidId_ReturnsUser() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(userDao.getById(userId)).thenReturn(expectedUser);

        User result = userService.readById(userId);

        assertEquals(expectedUser, result);
        verify(userDao, times(1)).getById(userId);
    }

    @Test
    void readById_NonExistingId_ThrowsUsernameNotFoundException() {
        Long userId = 1L;

        when(userDao.getById(userId)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.readById(userId));

        verify(userDao, times(1)).getById(userId);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<User> expectedUsers = new ArrayList<>();
        // Add some users to expectedUsers list

        when(userDao.getAll()).thenReturn(expectedUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(expectedUsers, result);
        verify(userDao, times(1)).getAll();
    }

    @Test
    void findUserByEmail_ValidEmail_ReturnsOptionalUser() {
        String email = "test@example.com";
        User expectedUser = new User();
        // Set properties of expectedUser

        when(userDao.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        Optional<User> result = userService.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userDao, times(1)).findByEmail(email);
    }

    @Test
    void findUserByEmail_NonExistingEmail_ReturnsEmptyOptional() {
        String email = "test@example.com";

        when(userDao.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserByEmail(email);

        assertFalse(result.isPresent());
        verify(userDao, times(1)).findByEmail(email);
    }

    @Test
    void getUserByEmail_ValidEmail_ReturnsUser() {
        String email = "test@example.com";
        User expectedUser = new User();
        // Set properties of expectedUser

        when(userDao.getUserByEmail(email)).thenReturn(expectedUser);

        User result = userService.getUserByEmail(email);

        assertEquals(expectedUser, result);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    void update_NullUserDto_ThrowsNullEntityReferenceException() {
        assertThrows(NullEntityReferenceException.class, () -> userService.update(null));

        verify(userDao, never()).getById(anyLong());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userDao, never()).update(any(User.class));
    }

    @Test
    void delete_ValidId_Success() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userDao.getById(userId)).thenReturn(existingUser);

        userService.delete(userId);

        verify(userDao, times(1)).getById(userId);
        verify(userDao, times(1)).delete(existingUser);
    }

    @Test
    void delete_NonExistingId_ThrowsUsernameNotFoundException() {
        Long userId = 1L;

        when(userDao.getById(userId)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.delete(userId));

        verify(userDao, times(1)).getById(userId);
    }
}