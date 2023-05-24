package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballerRepository extends CrudRepository<Footballer , Integer> {
    /*Запускаєтся*/
    /*
    @Query("SELECT f FROM FootballerDb f WHERE f.country = :country")
    List<Footballer> findByCountry(@Param("country") String country);
    */


    Optional<Footballer> findById(Integer randomId);



}
