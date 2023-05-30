package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class FootballerServiceTest {

    @Mock
    private FootballerRepository footballerRepository;

    @InjectMocks
    private FootballerService footballerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCount() {
        long expectedCount = 5;

        when(footballerRepository.count()).thenReturn(expectedCount);

        long actualCount = footballerService.count();

        Assertions.assertEquals(expectedCount, actualCount);
        verify(footballerRepository).count();
    }

    @Test
    void testSaveAll() {
        List<Footballer> footballers = Arrays.asList(
                new Footballer(),
                new Footballer()
        );

        when(footballerRepository.saveAll(footballers)).thenReturn(footballers);

        Iterable<Footballer> savedFootballers = footballerService.saveAll(footballers);

        Assertions.assertEquals(footballers, savedFootballers);
        verify(footballerRepository).saveAll(footballers);
    }

    @Test
    void testFindById() {
        int randomId = 1;
        Footballer footballer = new Footballer();

        when(footballerRepository.findById(randomId)).thenReturn(Optional.of(footballer));

        Optional<Footballer> foundFootballer = footballerService.findById(randomId);

        Assertions.assertTrue(foundFootballer.isPresent());
        Assertions.assertEquals(footballer, foundFootballer.get());
        verify(footballerRepository).findById(randomId);
    }
}
