package com.example.GuessFootballerBot.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface FootballerRepository extends CrudRepository<Footballer , Integer> {
    @Query("SELECT f FROM FootballerDb f WHERE f.country = :country")
    List<Footballer> findByCountry(@Param("country") String country);
}
