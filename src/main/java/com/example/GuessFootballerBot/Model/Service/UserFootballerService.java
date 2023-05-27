package com.example.GuessFootballerBot.Model.Service;

import com.example.GuessFootballerBot.Model.UserFootballer;
import com.example.GuessFootballerBot.Model.UserFootballerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFootballerService {

    UserFootballerRepository userFootballerRepository;

    @Autowired
    public UserFootballerService(UserFootballerRepository userFootballerRepository){
        this.userFootballerRepository = userFootballerRepository;
    }

//   Метод додає в таблицю chatId та footballerId
    public void addUserFootballer(Long chatId , Integer footballerId){
        UserFootballer userFootballer = new UserFootballer();
        userFootballer.setChatId(chatId);
        userFootballer.setFootballerId(footballerId);
        userFootballerRepository.save(userFootballer);

    }

//   Метод повертає список з footballerId певного користувача за його chatId
    public List<Integer> findUserFootballersByChatId(Long chatId ){
        return userFootballerRepository.findUserFootballersByChatId(chatId);
    }

//   Метод дозволяє видаляти поля в таблиці
    @Transactional
    public void deleteRowsByChatId(Long chatId){
        userFootballerRepository.deleteByChatId(chatId);
    }

}
