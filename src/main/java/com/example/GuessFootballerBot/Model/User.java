package com.example.GuessFootballerBot.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity клас, що є таблицею UserDb
 */
@Data
@Entity(name = "UserDb")
public class User {
    @Id
    Long chatId;

    String UserName;

    Integer points;

}
