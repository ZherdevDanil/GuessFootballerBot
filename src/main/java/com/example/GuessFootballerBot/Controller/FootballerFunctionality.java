package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import com.example.GuessFootballerBot.Model.Service.FootballerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;
import java.util.Random;
//import org.telegram.telegrambots.meta.api.objects.File;


@Component
public class FootballerFunctionality {

    public Footballer currentFootballer;
    @Autowired
    private FootballerService footballerService;
    @Autowired
    private FootballerRepository footballerRepository;
    private static final int FOOTBALLERS_COUNT = 51;

    public int genereateRandomFootballerId(){
        Random random = new Random();
        int randomFootballerId = random.nextInt((FOOTBALLERS_COUNT)+1);
        return randomFootballerId;
    }


    public void getFootballer(){
        Optional<Footballer> footballer;
        footballer = footballerService.findById(genereateRandomFootballerId());
        currentFootballer = footballer.get();
        System.out.println(currentFootballer);
    }


    public String getFootballerFullName(Footballer currentFootballer){
        String currentfootballerFullname = currentFootballer.getFullname();
        return currentfootballerFullname;

    }

    public SendMessage getFootballerName(Long chatId , Footballer currentFootballer){
        String currentFootballerName = currentFootballer.getName();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerName);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerPosition(Long chatId , Footballer currentFootballer){
        String currentFootballerPosition = currentFootballer.getPosition();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerPosition);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }

    public SendMessage getFootballerStillPlay(Long chatId , Footballer currentFootballer){
        String currentfootballerStillplay = currentFootballer.getStillplay();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerStillplay);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }

    public SendMessage getFootballerCountry(Long chatId , Footballer currentFootballer){
        String currentfootballerCountry = currentFootballer.getCountry();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerCountry);
        BotFunctionality botFunctionality;
        return message;

        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerSurname(Long chatId , Footballer currentFootballer){
        String currentFootballerSurname = currentFootballer.getSurname();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerSurname);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerClubs(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs = currentFootballer.getClubs();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }

    public SendMessage getFootballerClubs1(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs1 = currentFootballer.getClubs1();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs1);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
         */

    }
    public SendMessage getFootballerClubs2(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs2 = currentFootballer.getClubs2();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs2);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

         */

    }
    public SendMessage getFootballerClubs3(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs3 = currentFootballer.getClubs3();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs3);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


         */
    }
    public SendMessage getFootballerClubs4(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs4 = currentFootballer.getClubs4();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs4);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

         */

    }
    public SendMessage getFootballerClubs5(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs5 = currentFootballer.getClubs5();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs5);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerClubs6(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs6 = currentFootballer.getClubs6();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs6);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerClubs7(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs7 = currentFootballer.getClubs7();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs7);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }
    public SendMessage getFootballerClubs8(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs8 = currentFootballer.getClubs8();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs8);
        return message;
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/

    }


}
