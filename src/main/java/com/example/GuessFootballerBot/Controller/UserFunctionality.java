package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Service.UserService;
import com.example.GuessFootballerBot.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * клас, що є контроллером, отримає інформацію, та або оновлює таблицю User,
 * або бере дані з таблиці User та передає в view для їх виводу
 */
@Component
public class UserFunctionality {
    @Autowired
    UserService userService;

    @Bean
    public UserService getUserService() {
        return userService;
    }

    /**
     * Метод оновляє дані в таблиці, додає points юзеру в таблиці UserDb
     * @param chatId
     * @param points
     */
    public void updateUser(Long chatId, Integer points) {
        try {
            if (userService.existsByChatId(chatId)) {
                User user = userService.findByChatId(chatId);
                user.setPoints(user.getPoints() + points);
                userService.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Не вдалося оновити дані", e);
        }

    }

    /**
     * Метод зберігає юзера в таблиці
     * @param chatId
     * @param userName
     * @param points
     */
    public void saveUser(Long chatId, String userName, Integer points) {
        try {
            if (!userService.existsByChatId(chatId)) {
                User newUser = new User();
                newUser.setChatId(chatId);
                newUser.setUserName(userName);
                newUser.setPoints(points);
                userService.save(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Не вдалося зберегти ", e);
        }


    }

    /**
     * метод виводить Iнформацію про користувача, його ім'я та кількість очок, якщо юзера не існує, то він додається в таблицю,
     * якщо юзер існує в таблиці то виводиться інформація
     * @param chatId
     * @param userName
     * @return
     */

    public SendMessage infoUser(Long chatId, String userName) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Не вдалося отримати та обобити інформацію", e);
        }

    }
}
