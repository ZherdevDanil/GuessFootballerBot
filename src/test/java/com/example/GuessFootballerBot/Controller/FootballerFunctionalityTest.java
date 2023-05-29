package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.Service.FootballerService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FootballerFunctionalityTest {
    @Autowired
    FootballerService footballerService;
    @Test
    void testGetFootballer() {
        FootballerFunctionality footballerFunctionality = new FootballerFunctionality(footballerService);
        footballerFunctionality.getFootballer();
        Footballer randomFootballer = footballerFunctionality.getCurrentFootballer();
        assertNotNull(randomFootballer);
    }



}