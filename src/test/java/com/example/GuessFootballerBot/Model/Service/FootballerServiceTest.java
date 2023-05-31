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

/**
 * Клас, що тестує методи класу FootballerService
 */
class FootballerServiceTest {

    @Mock
    private FootballerRepository footballerRepository;

    @InjectMocks
    private FootballerService footballerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * тест перевіряє, що метод count() повертає правильну кількість футболістів шляхом перевірки поверненого значення з репозиторію
     */
    @Test
    void testCount() {
        long expectedCount = 5;

        when(footballerRepository.count()).thenReturn(expectedCount);

        long actualCount = footballerService.count();

        Assertions.assertEquals(expectedCount, actualCount);
        verify(footballerRepository).count();
    }

    /**
     * тест перевіряє, що метод saveAll() зберігає список футболістів і повертає їх, порівнюючи повернене значення з репозиторію з очікуваним списком футболістів
     */
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

    /**
     * тест перевіряє, що метод findById() повертає футболіста з правильним ідентифікатором, перевіряючи, чи повернуто значення Optional містить футболіста з репозиторію і порівнюючи його з очікуваним футболістом
     */
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
