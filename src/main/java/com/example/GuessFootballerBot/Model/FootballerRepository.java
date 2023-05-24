package com.example.GuessFootballerBot.Model;

import com.example.GuessFootballerBot.Model.Service.FootballerService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballerRepository extends CrudRepository<Footballer , Integer> {
    @Override
    Optional<Footballer> findById(Integer randomId);
}
