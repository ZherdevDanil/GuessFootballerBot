package com.example.GuessFootballerBot.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
    Таблиця з футболістами FootballerDb
 */
@Data
@Entity(name = "FootballerDb")
public class Footballer{
    @Id
    private int id;
    private String name;
    private String surname;
    private String fullname;
    private String country;
    private String clubs;
    private String clubs1;
    private String clubs2;
    private String clubs3;
    private String clubs4;
    private String clubs5;
    private String clubs6;
    private String clubs7;
    private String clubs8;
    private String position;
    private String stillplay;


}
