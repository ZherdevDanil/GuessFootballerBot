package com.example.GuessFootballerBot.Model.Service;


import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }


    public User findByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }


    public boolean existsByChatId(Long chatId) {
        return userRepository.existsByChatId(chatId);
    }
    /*
    public Optional<User> findByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }

     */
    public List<User> getTopUsers() {
        return userRepository.findTop3ByOrderByPointsDesc();
    }
}
