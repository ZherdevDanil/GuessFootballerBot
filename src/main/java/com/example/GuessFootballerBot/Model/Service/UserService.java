package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
    Клас для реалізації методів з UserRepository
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
/**  Метод зберігає юзера в бд*/
    public void save(User user){
        userRepository.save(user);
    }

/**  Метод шукає та повертає юзера за його chatId*/
    public User findByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }

/**  Метод перевіряє чи є користувач з chatId в таблиці*/
    public boolean existsByChatId(Long chatId) {
        return userRepository.existsByChatId(chatId);
    }
/**  метод повертає список з 3 екземплярів класа User, в яких найбільша кількість points в бд*/
    public List<User> getTopUsers() {
        return userRepository.findTop3ByOrderByPointsDesc();
    }
}
