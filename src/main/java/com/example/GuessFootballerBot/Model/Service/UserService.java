package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Сервіс клас що реалізає методи з UserRepository для подальшої роботи з ними в контроллерах
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод зберігає юзера в бд
     * @param user
     */
    public void save(User user) {
        try {
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод шукає та повертає юзера за його chatId
     * @param chatId
     * @return
     */
    public User findByChatId(Long chatId) {
        try {
            return userRepository.findByChatId(chatId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Метод перевіряє чи є користувач з chatId в таблиці
     * @param chatId
     * @return
     */
    public boolean existsByChatId(Long chatId) {
        try {
            return userRepository.existsByChatId(chatId);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * метод повертає список з 3 екземплярів класа User, в яких найбільша кількість points в бд
     * @return
     */
    public List<User> getTopUsers() {
        try {
            return userRepository.findTop3ByOrderByPointsDesc();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
