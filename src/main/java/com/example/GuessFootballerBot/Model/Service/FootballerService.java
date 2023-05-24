package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballerService {

    @Autowired
    private FootballerRepository footballerRepository;

    public void save(Footballer footballer){
        footballerRepository.save(footballer);

    }


    public void existbyid(Footballer footballer){
    }


    //public List<Footballer>




}
