package com.example.GuessFootballerBot.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
* База даних UserDb
* */

@Data
@Entity(name = "UserDb")
public class User {
    @Id
    Long chatId;

    String UserName;

    Integer points;

}
