package com.example.GuessFootballerBot.Model.Service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testSave() {
        User user = new User();

        // Configure the userRepository.save() method to capture the saved user
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            User savedUser = (User) args[0];
            assertEquals(user, savedUser);
            return null;
        }).when(userRepository).save(user);

        userService.save(user);

        verify(userRepository).save(user);
    }



    @Test
    void testFindByChatId_UserExists() {
        User user = new User();
        doReturn(Optional.of(user)).when(userRepository).findByChatId(1L);

        User result = userService.findByChatId(1L);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository).findByChatId(1L);
    }

    @Test
    void testFindByChatId_UserNotExists() {
        doReturn(Optional.empty()).when(userRepository).findByChatId(1L);

        User result = userService.findByChatId(1L);

        assertNull(result);
        verify(userRepository).findByChatId(1L);
    }
    @Test
    void testExistsByChatId_True() {
        when(userRepository.existsByChatId(1L)).thenReturn(true);

        boolean result = userService.existsByChatId(1L);

        assertTrue(result);
        verify(userRepository).existsByChatId(1L);
    }

    @Test
    void testExistsByChatId_False() {
        when(userRepository.existsByChatId(1L)).thenReturn(false);

        boolean result = userService.existsByChatId(1L);

        assertFalse(result);
        verify(userRepository).existsByChatId(1L);
    }

    // Тестирование getTopUsers() зависит от реализации UserRepository.
    // В данном примере предполагается, что метод findTop3ByOrderByPointsDesc()
    // возвращает список пользователей.

    @Test
    void testGetTopUsers() {
        List<User> users = new ArrayList<>();
        when(userRepository.findTop3ByOrderByPointsDesc()).thenReturn(users);

        List<User> result = userService.getTopUsers();

        assertNotNull(result);
        assertEquals(users, result);
        verify(userRepository).findTop3ByOrderByPointsDesc();
    }
}
