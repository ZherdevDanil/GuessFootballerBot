package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Інтерфейс UserRepository наслідує інтерфейс CrudRepository (Create Read Update Delete) для
 * отримання методів роботи з бд User
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Пошук юзера за його chatId
     * @param chatId
     * @return
     */
    User findByChatId(Long chatId);

    /**
     * Метод перевіряє чи існує користувач в таблиці
     * @param chatId
     * @return
     */
    boolean existsByChatId(Long chatId);

    /**
     * Метод для виводу списку з 3 юзерів що мають нійбільшу кількість points  бд
     * @return
     */
    List<User> findTop3ByOrderByPointsDesc();

}
