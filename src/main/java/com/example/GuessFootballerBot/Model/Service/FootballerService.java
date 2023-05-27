package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FootballerService {

    private final FootballerRepository footballerRepository;
    @Autowired
    public FootballerService(FootballerRepository footballerRepository) {
        this.footballerRepository = footballerRepository;
    }

/**    метод повертає кількість полів в таблиці FootballerDb*/
    public long count(){
        return footballerRepository.count();
    }

/**    метод зберігає List об'єктами класу Footballer в бд FootballerDB*/

    public Iterable<Footballer> saveAll(List<Footballer> footballers) {
        return footballerRepository.saveAll(footballers);
    }

/**    Реалізація методу з інтерфейсу FootballerRepository*/
    public Optional<Footballer> findById(Integer randomId){
        return footballerRepository.findById(randomId);
    }
}
