package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Service.UserService;
import com.example.GuessFootballerBot.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Component
public class UserFunctionality {
    @Autowired
    UserService userService;

    @Bean
    public UserService getUserService() {
        return userService;
    }

    public void updateUser(Long chatId, Integer points){
        if(userService.existsByChatId(chatId)){
            User user = userService.findByChatId(chatId);
            user.setPoints(user.getPoints()+points);
            userService.save(user);
        }
    }

    public void saveUser(Long chatId, String userName , Integer points){
        if (!userService.existsByChatId(chatId)){
            User newUser = new User();
            newUser.setChatId(chatId);
            newUser.setUserName(userName);
            newUser.setPoints(points);
            userService.save(newUser);
        }
    }

    public SendMessage infoUser(Long chatId , String userName){
        if (userService.existsByChatId(chatId)) {
            User user = userService.findByChatId(chatId);
            String currentUserName = user.getUserName();
            int currentUserPoints = user.getPoints();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ім'я користувача : " + currentUserName + " \nКількість очок : " + currentUserPoints);
            return message;
        } else {
            User newUser = new User();
            newUser.setChatId(chatId);
            newUser.setUserName(userName);
            newUser.setPoints(0);
            userService.save(newUser);
            String currentUserName = newUser.getUserName();
            int currentUserPoints = newUser.getPoints();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ім'я користувача : " + currentUserName + "\nКількість очок : " + currentUserPoints);
            return message;
        }
    }
}
