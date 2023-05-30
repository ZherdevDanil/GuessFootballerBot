package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Service.UserService;
import com.example.GuessFootballerBot.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.mockito.Mockito.*;

class UserFunctionalityTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFunctionality userFunctionality;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser_UserExists() {
        Long chatId = 123456L;
        Integer points = 50;

        User existingUser = new User();
        existingUser.setChatId(chatId);
        existingUser.setPoints(100);

        when(userService.existsByChatId(chatId)).thenReturn(true);
        when(userService.findByChatId(chatId)).thenReturn(existingUser);

        userFunctionality.updateUser(chatId, points);

        Assertions.assertEquals(150, existingUser.getPoints());
        verify(userService).save(existingUser);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        Long chatId = 123456L;
        Integer points = 50;

        when(userService.existsByChatId(chatId)).thenReturn(false);

        userFunctionality.updateUser(chatId, points);

        verify(userService, never()).findByChatId(chatId);
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void testSaveUser_UserDoesNotExist() {
        Long chatId = 123456L;
        String userName = "TestUser";
        Integer points = 100;

        when(userService.existsByChatId(chatId)).thenReturn(false);

        userFunctionality.saveUser(chatId, userName, points);

        User newUser = new User();
        newUser.setChatId(chatId);
        newUser.setUserName(userName);
        newUser.setPoints(points);

        verify(userService).save(newUser);
    }

    @Test
    void testSaveUser_UserExists() {
        Long chatId = 123456L;
        String userName = "TestUser";
        Integer points = 100;

        when(userService.existsByChatId(chatId)).thenReturn(true);

        userFunctionality.saveUser(chatId, userName, points);

        verify(userService, never()).save(any(User.class));
    }

    @Test
    void testInfoUser_UserExists() {
        Long chatId = 123456L;
        String userName = "TestUser";

        User existingUser = new User();
        existingUser.setChatId(chatId);
        existingUser.setUserName(userName);
        existingUser.setPoints(100);

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(chatId);
        expectedMessage.setText("Ім'я користувача : " + userName + " \nКількість очок : 100");

        when(userService.existsByChatId(chatId)).thenReturn(true);
        when(userService.findByChatId(chatId)).thenReturn(existingUser);

        SendMessage message = userFunctionality.infoUser(chatId, userName);

        Assertions.assertEquals(expectedMessage, message);
        verify(userService).findByChatId(chatId);
    }

    @Test
    void testInfoUser_UserDoesNotExist() {
        Long chatId = 123456L;
        String userName = "TestUser";

        User newUser = new User();
        newUser.setChatId(chatId);
        newUser.setUserName(userName);
        newUser.setPoints(0);

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(chatId);
        expectedMessage.setText("Ім'я користувача : " + userName + "\nКількість очок : 0");

        when(userService.existsByChatId(chatId)).thenReturn(false);

        SendMessage message = userFunctionality.infoUser(chatId, userName);

        Assertions.assertEquals(expectedMessage, message);
        verify(userService).save(newUser);
    }
}