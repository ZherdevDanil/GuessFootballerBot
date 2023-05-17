package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FootballerRepository extends CrudRepository<Footballer , Integer> {
}
