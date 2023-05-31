package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    /**
     * тест перевіряє, що метод save() викликається з правильним користувачем при збереженні користувача
     */
    @Test
    public void testSaveUser() {
        // Создание тестового пользователя
        User user = new User();
        user.setChatId(123456L);
        user.setUserName("TestUser");
        user.setPoints(100);

        // Выполнение сохранения пользователя
        userService.save(user);

        // Проверка, что метод save был вызван с правильным пользователем
        verify(userRepository).save(user);
    }

    /**
     *тест перевіряє, що метод findByChatId() повертає очікуваного користувача, коли користувач з заданим chatId існує в репозиторії
     */
    @Test
    public void testFindByChatId_UserExists() {
        // Подготовка данных для теста
        Long chatId = 123456L;
        User expectedUser = new User();
        expectedUser.setChatId(chatId);

        // Настройка поведения репозитория
        when(userRepository.findByChatId(chatId)).thenReturn(expectedUser);

        // Выполнение метода, который тестируем
        User result = userService.findByChatId(chatId);

        // Проверка результата
        Assertions.assertEquals(expectedUser, result);
        verify(userRepository).findByChatId(chatId);
    }

    /**
     *тест перевіряє, що метод findByChatId() повертає null, коли користувач з заданим chatId не існує в репозиторії.
     */
    @Test
    public void testFindByChatId_UserDoesNotExist() {
        // Подготовка данных для теста
        Long chatId = 123456L;

        // Настройка поведения репозитория
        when(userRepository.findByChatId(chatId)).thenReturn(null);

        // Выполнение метода, который тестируем
        User result = userService.findByChatId(chatId);

        // Проверка результата
        Assertions.assertNull(result);
        verify(userRepository).findByChatId(chatId);
    }

    /**
     * тест перевіряє, що метод existsByChatId() повертає true, коли користувач з заданим chatId існує в репозиторії
     *
     */
    @Test
    public void testExistsByChatId_UserExists() {
        // Подготовка данных для теста
        Long chatId = 123456L;

        // Настройка поведения репозитория
        when(userRepository.existsByChatId(chatId)).thenReturn(true);

        // Выполнение метода, который тестируем
        boolean result = userService.existsByChatId(chatId);

        // Проверка результата
        Assertions.assertTrue(result);
        verify(userRepository).existsByChatId(chatId);
    }
    /**
     *тест перевіряє, що метод existsByChatId() повертає false, коли користувач з заданим chatId не існує в репозиторії
     */

    @Test
    public void testExistsByChatId_UserDoesNotExist() {
        // Подготовка данных для теста
        Long chatId = 123456L;

        // Настройка поведения репозитория
        when(userRepository.existsByChatId(chatId)).thenReturn(false);

        // Выполнение метода, который тестируем
        boolean result = userService.existsByChatId(chatId);

        // Проверка результата
        Assertions.assertFalse(result);
        verify(userRepository).existsByChatId(chatId);
    }

    /**
     *тест перевіряє, що метод getTopUsers() повертає очікуваний список користувачів, коли викликається метод findTop3ByOrderByPointsDesc() в репозиторії
     */
    @Test
    public void testGetTopUsers() {
        // Подготовка данных для теста
        List<User> expectedUsers = Collections.singletonList(new User());

        // Настройка поведения репозитория
        when(userRepository.findTop3ByOrderByPointsDesc()).thenReturn(expectedUsers);

        // Выполнение метода, который тестируем
        List<User> result = userService.getTopUsers();

        // Проверка результата
        Assertions.assertEquals(expectedUsers, result);
        verify(userRepository).findTop3ByOrderByPointsDesc();
    }
}
