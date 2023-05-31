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

/**
 * клас,що тестує методи класу UserFunctionality
 */
class UserFunctionalityTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFunctionality userFunctionality;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     *  тест перевіряє, що метод updateUser()
     *  коректно оновлює користувача, якщо користувач існує. Перевіряється,
     *  чи викликано методи existsByChatId() та findByChatId() з вірними аргументами, чи оновлено кількість очок користувача та чи викликано метод save() для збереження оновленого користувача
     */
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

    /**
     * тест перевіряє, що метод updateUser() не робить жодних змін, якщо користувач не існує. Перевіряється, чи викликано метод existsByChatId()
     * з вірним аргументом та чи не викликано методи findByChatId() та save()
     */
    @Test
    void testUpdateUser_UserDoesNotExist() {
        Long chatId = 123456L;
        Integer points = 50;

        when(userService.existsByChatId(chatId)).thenReturn(false);

        userFunctionality.updateUser(chatId, points);

        verify(userService, never()).findByChatId(chatId);
        verify(userService, never()).save(any(User.class));
    }

    /**
     * тест перевіряє, що метод saveUser() створює нового користувача і зберігає його, якщо користувач не існує. Перевіряється, чи викликано метод existsByChatId() з вірним аргументом
     * та чи викликано метод save() для збереження нового користувача.
     */
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

    /**
     * тест перевіряє, що метод saveUser() не створює нового користувача і не зберігає його, якщо користувач вже існує. Перевіряється, чи викликано метод
     * existsByChatId() з вірним аргументом та чи не викликано метод save()
     */
    @Test
    void testSaveUser_UserExists() {
        Long chatId = 123456L;
        String userName = "TestUser";
        Integer points = 100;

        when(userService.existsByChatId(chatId)).thenReturn(true);

        userFunctionality.saveUser(chatId, userName, points);

        verify(userService, never()).save(any(User.class));
    }

    /**
     * тест перевіряє, що метод infoUser() повертає правильне повідомлення з інформацією про користувача, якщо користувач існує. Перевіряється, чи викликано метод existsByChatId() з вірним аргументом, чи викликано метод findByChatId() з вірним аргументом, чи повернуто очікуване повідомлення
     * та чи викликано метод findByChatId() для збереження нового користувача.
     */
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

    /**
     *  тест перевіряє, що метод infoUser() повертає правильне повідомлення з інформацією про користувача, якщо користувач не існує.
     *  Перевіряється, чи викликано метод existsByChatId() з вірним аргументом, чи повернуто очікуване повідомлення та чи викликано метод save() для збереження нового користувача.
     */
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