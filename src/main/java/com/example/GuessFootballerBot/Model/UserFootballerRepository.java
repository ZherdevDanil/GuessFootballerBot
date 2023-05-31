package com.example.GuessFootballerBot.Model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFootballerRepository extends CrudRepository<UserFootballer, Integer> {

    /**
     * Запит видаляє всі поля таблиці з певним chatId
     * @param chatId
     */
    @Modifying
    @Query("DELETE FROM UserFootballerr userfootballer WHERE userfootballer.chatId = :chatId")
    void deleteByChatId(@Param("chatId") Long chatId);

    /**
     * Запит повертає список footballerId які відповідають певному користувачу з його chatId
     * @param chatId
     * @return
     */
    @Query("SELECT userfootballer.footballerId FROM UserFootballerr userfootballer WHERE userfootballer.chatId = :chatId")
    List<Integer> findUserFootballersByChatId(@Param("chatId") Long chatId);
}
