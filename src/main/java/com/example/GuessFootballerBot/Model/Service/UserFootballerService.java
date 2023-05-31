package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.UserFootballer;
import com.example.GuessFootballerBot.Model.UserFootballerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

/**
 * Сервіс клас, що реалізує методи в UserFootballerRepository для подальшої роботи з ними в контроллерах
 */
@Service
public class UserFootballerService {

    UserFootballerRepository userFootballerRepository;

    @Autowired
    public UserFootballerService(UserFootballerRepository userFootballerRepository) {
        this.userFootballerRepository = userFootballerRepository;
    }

    /**
     * Метод додає в таблицю chatId та footballerId
     * @param chatId
     * @param footballerId
     */
    public void addUserFootballer(Long chatId, Integer footballerId) {
        try {
            UserFootballer userFootballer = new UserFootballer();
            userFootballer.setChatId(chatId);
            userFootballer.setFootballerId(footballerId);
            userFootballerRepository.save(userFootballer);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Метод повертає список з footballerId певного користувача за його chatId
     * @param chatId
     * @return
     */
    public List<Integer> findUserFootballersByChatId(Long chatId) {
        try {
            return userFootballerRepository.findUserFootballersByChatId(chatId);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Метод дозволяє видаляти поля в таблиці
     * @param chatId
     */
    @Transactional
    public void deleteRowsByChatId(Long chatId) {
        try {
            userFootballerRepository.deleteByChatId(chatId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
