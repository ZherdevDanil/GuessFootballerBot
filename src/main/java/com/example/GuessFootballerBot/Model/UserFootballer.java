package com.example.GuessFootballerBot.Model;

import jakarta.persistence.*;
import lombok.Data;
/**
 * Таблиця, створена для встановлення відповідності User chatId Footballer id , для перевірки чи відгадував користувач
 * такого футболіста
 * */
@Data
@Entity(name = "UserFootballerr")
public class UserFootballer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Long chatId;

    @JoinColumn(name = "footballerId" , referencedColumnName = "id")
    private Integer footballerId;


}
