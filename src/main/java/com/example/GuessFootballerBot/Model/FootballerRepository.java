package com.example.GuessFootballerBot.Model;

import com.example.GuessFootballerBot.Model.Service.FootballerService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT f.name FROM FootballerDb f WHERE f.id = :randomId")
    String getByName(@Param("randomId") Integer randomId);

}
