package com.example.GuessFootballerBot.Model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "UserFootballer")
public class UserFootballer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long chatId;

    @JoinColumn(name = "footballerId" , referencedColumnName = "id")
    private Integer footballerId;


}
