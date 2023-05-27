package com.example.GuessFootballerBot.Model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFootballerRepository extends CrudRepository<UserFootballer , Integer> {

/**  Запит видаляє всі поля таблиці з певним chatId*/
    @Modifying
    @Query("DELETE FROM UserFootballer uf WHERE uf.chatId = :chatId")
    void deleteByChatId(@Param("chatId") Long chatId);

/**  Запит повертає список footballerId які відповідають певному користувачу з його chatId*/
    @Query("select userfootballer.footballerId from UserFootballer userfootballer where userfootballer.chatId = :chat_id")
    List<Integer> findUserFootballersByChatId(@Param("chat_id") Long chat_id);
}
