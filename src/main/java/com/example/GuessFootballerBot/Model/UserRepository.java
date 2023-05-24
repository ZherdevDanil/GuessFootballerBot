package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<User , Long> {
    /*
    User findbyChatId(Long chatId);


    boolean existsByChatId(Long chatId);
     */
}
