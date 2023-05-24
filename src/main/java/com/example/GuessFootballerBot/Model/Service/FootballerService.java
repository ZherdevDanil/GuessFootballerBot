package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballerService {

    private final FootballerRepository footballerRepository;
    @Autowired
    public FootballerService(FootballerRepository footballerRepository) {
        this.footballerRepository = footballerRepository;
    }

    public long count(){
        return footballerRepository.count();
    }

    public Iterable<Footballer> saveAll(List<Footballer> footballers) {
        return footballerRepository.saveAll(footballers);
    }

}
