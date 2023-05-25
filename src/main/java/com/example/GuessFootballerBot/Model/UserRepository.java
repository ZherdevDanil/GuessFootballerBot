package com.example.GuessFootballerBot.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User , Long> {

    User findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);

    List<User> findTop3ByOrderByPointsDesc();

}
