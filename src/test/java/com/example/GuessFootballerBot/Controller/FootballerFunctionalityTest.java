package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.Service.FootballerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FootballerFunctionalityTest {


    @Mock
    private FootballerService footballerService;

    @Mock
    private UserFunctionality userFunctionality;

    @InjectMocks
    private FootballerFunctionality footballerFunctionality;



    @Test
    void testGetFootballer() {
        int randomId = 5;
        Footballer footballer = new Footballer();
        footballer.setId(randomId);

        when(footballerService.findById(randomId)).thenReturn(Optional.of(footballer));

        footballerFunctionality.getFootballer();

        Footballer currentFootballer = footballerFunctionality.getCurrentFootballer();

        Assertions.assertNotNull(currentFootballer);
    }

    @Test
    void testGetFootballerFullName() {
        Footballer footballer = new Footballer();
        footballer.setFullname("Lionel Messi");

        String fullName = footballerFunctionality.getFootballerFullName(footballer);

        Assertions.assertNotNull(fullName);
    }

    @Test
    void testGetFootballerName() {
        Long chatId = 123456L;
        Footballer footballer = new Footballer();
        footballer.setName("Messi");

        SendMessage message = footballerFunctionality.getFootballerName(chatId, footballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getChatId());
        Assertions.assertNotNull(message.getText());
    }
    @Test
    void testGetFootballerPosition() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setPosition("Forward");

        SendMessage message = footballerFunctionality.getFootballerPosition(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Forward", message.getText());
    }

    @Test
    void testGetFootballerStillPlay() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setStillplay("Yes");

        SendMessage message = footballerFunctionality.getFootballerStillPlay(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Yes", message.getText());
    }

    @Test
    void testGetFootballerCountry() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setCountry("Spain");

        SendMessage message = footballerFunctionality.getFootballerCountry(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Spain", message.getText());
    }

    @Test
    void testGetFootballerSurname() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setSurname("Ronaldo");

        SendMessage message = footballerFunctionality.getFootballerSurname(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Ronaldo", message.getText());
    }

    @Test
    void testGetFootballerClubs() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setClubs("Real Madrid, Manchester United");

        SendMessage message = footballerFunctionality.getFootballerClubs(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Real Madrid, Manchester United", message.getText());
    }

    @Test
    void testGetFootballerClubs1() {
        Long chatId = 123456L;
        Footballer currentFootballer = new Footballer();
        currentFootballer.setClubs1("Real Madrid");

        SendMessage message = footballerFunctionality.getFootballerClubs1(chatId, currentFootballer);

        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getText());
        Assertions.assertEquals("Real Madrid", message.getText());
    }


}