package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FootballerRepository extends CrudRepository<Footballer , Integer> {
}
