package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.UserFootballer;
import com.example.GuessFootballerBot.Model.UserFootballerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class UserFootballerServiceTest {

    @Mock
    private UserFootballerRepository userFootballerRepository;

    @InjectMocks
    private UserFootballerService userFootballerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * тест перевіряє, що метод addUserFootballer() зберігає користувача-футболіста з правильним chatId і footballerId в репозиторії
     */
    @Test
    void testAddUserFootballer() {
        Long chatId = 123456L;
        Integer footballerId = 1;

        UserFootballer userFootballer = new UserFootballer();
        userFootballer.setChatId(chatId);
        userFootballer.setFootballerId(footballerId);

        userFootballerService.addUserFootballer(chatId, footballerId);

        verify(userFootballerRepository).save(userFootballer);
    }

    /**
     * тест перевіряє, що метод findUserFootballersByChatId() повертає список футболістів з правильним chatId з репозиторію
     */
    @Test
    void testFindUserFootballersByChatId() {
        Long chatId = 123456L;
        List<Integer> expectedFootballerIds = Arrays.asList(1, 2, 3);

        when(userFootballerRepository.findUserFootballersByChatId(chatId)).thenReturn(expectedFootballerIds);

        List<Integer> foundFootballerIds = userFootballerService.findUserFootballersByChatId(chatId);

        Assertions.assertEquals(expectedFootballerIds, foundFootballerIds);
        verify(userFootballerRepository).findUserFootballersByChatId(chatId);
    }

    /**
     * тест перевіряє, що метод findUserFootballersByChatId() повертає пустий список, якщо виникає виключна ситуація при виконанні запиту до репозиторію
     */
    @Test
    void testFindUserFootballersByChatId_WithException() {
        Long chatId = 123456L;

        when(userFootballerRepository.findUserFootballersByChatId(chatId)).thenThrow(new RuntimeException());

        List<Integer> foundFootballerIds = userFootballerService.findUserFootballersByChatId(chatId);

        Assertions.assertEquals(Collections.emptyList(), foundFootballerIds);
        verify(userFootballerRepository).findUserFootballersByChatId(chatId);
    }

    /**
     * тест перевіряє, що метод deleteRowsByChatId() видаляє рядки з правильним chatId з репозиторію
     */
    @Test
    void testDeleteRowsByChatId() {
        Long chatId = 123456L;

        userFootballerService.deleteRowsByChatId(chatId);

        verify(userFootballerRepository).deleteByChatId(chatId);
    }
}
