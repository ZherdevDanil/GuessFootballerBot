package com.example.GuessFootballerBot.Controller;
import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.Service.FootballerService;
import com.example.GuessFootballerBot.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;



@Component
public class FootballerFunctionality {
    @Autowired
    private UserFunctionality userFunctionality;

    private Footballer currentFootballer;

    public Footballer getCurrentFootballer() {
        return currentFootballer;
    }

    public void setCurrentFootballer(Footballer currentFootballer) {
        this.currentFootballer = currentFootballer;
    }

    private final int FOOTBALLERS_COUNT = 51;

    public int getFOOTBALLERS_COUNT() {
        return FOOTBALLERS_COUNT;
    }

    @Autowired
    private FootballerService footballerService;
    @Bean
    public FootballerService getFootballerService() {
        return footballerService;
    }

/**   Метод генераю випадкове id в діапазоні [1,51], кількість футболістів в таблиці 51*/
    public int genereateRandomFootballerId(){
        Random random = new Random();
        int randomFootballerId = random.nextInt((FOOTBALLERS_COUNT)+1);
        return randomFootballerId;
    }


/**  Метод отримую з таблиці екземлпяр класу Footballer, та присвоює його currentFootballer*/
    public void getFootballer() {
        Optional<Footballer> footballer = footballerService.findById(genereateRandomFootballerId());
        if (footballer.isPresent()) {
            currentFootballer = footballer.get();
            System.out.println(currentFootballer);
        } else {
            System.out.println("Error");
            getFootballer();
        }
    }

/**  Метод повертає повне ім'я currentFootballer*/
    public String getFootballerFullName(Footballer currentFootballer){
        String currentfootballerFullname = currentFootballer.getFullname();
        return currentfootballerFullname;
    }
/**  Метод повертає ім'я футболіста*/
    public SendMessage getFootballerName(Long chatId , Footballer currentFootballer){
        String currentFootballerName = currentFootballer.getName();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerName);
        return message;
    }
/**  Метод повертає позицію футболіста*/
    public SendMessage getFootballerPosition(Long chatId , Footballer currentFootballer){
        String currentFootballerPosition = currentFootballer.getPosition();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerPosition);
        return message;
    }
/**  метод повертає чи грає футболіст досі*/
    public SendMessage getFootballerStillPlay(Long chatId , Footballer currentFootballer){
        String currentfootballerStillplay = currentFootballer.getStillplay();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerStillplay);
        return message;
    }
/**  метод повертає країну де народився*/
    public SendMessage getFootballerCountry(Long chatId , Footballer currentFootballer){
        String currentfootballerCountry = currentFootballer.getCountry();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerCountry);
        BotFunctionality botFunctionality;
        return message;
    }
/**  метод повертає прізвище*/
    public SendMessage getFootballerSurname(Long chatId , Footballer currentFootballer){
        String currentFootballerSurname = currentFootballer.getSurname();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerSurname);
        return message;
    }
/**  метод повертає всі клуби в яких був гравець*/
    public SendMessage getFootballerClubs(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs = currentFootballer.getClubs();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs);
        return message;
    }
/**  метод повертає перший клуб*/
    public SendMessage getFootballerClubs1(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs1 = currentFootballer.getClubs1();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs1);
        return message;
    }
/**  метод повертає другий клуб*/

    public SendMessage getFootballerClubs2(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs2 = currentFootballer.getClubs2();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs2);
        return message;
    }
/**  метод повертає третій клуб*/

    public SendMessage getFootballerClubs3(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs3 = currentFootballer.getClubs3();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs3);
        return message;
    }
/**  метод повертає четвертий клуб*/

    public SendMessage getFootballerClubs4(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs4 = currentFootballer.getClubs4();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs4);
        return message;
    }
/**  метод повертає п'ятий клуб*/

    public SendMessage getFootballerClubs5(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs5 = currentFootballer.getClubs5();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs5);
        return message;
    }
/**  метод повертає шостий клуб*/

    public SendMessage getFootballerClubs6(Long chatId , Footballer currentFootballer){
        String currentfootballerClubs6 = currentFootballer.getClubs6();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentfootballerClubs6);
        return message;
    }
/**  метод повертає сьомий клуб*/

    public SendMessage getFootballerClubs7(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs7 = currentFootballer.getClubs7();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs7);
        return message;
    }
/**  метод повертає восьмий клуб*/

    public SendMessage getFootballerClubs8(Long chatId , Footballer currentFootballer){
        String currentFootballerClubs8 = currentFootballer.getClubs8();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(currentFootballerClubs8);
        return message;
    }
/**  метод повертає список з 3 юзерів з найбільшою кількістю points*/
    public List<SendMessage> getTopPlayers(Long chatId ){
        List<User> topusers = userFunctionality.getUserService().getTopUsers();
        List<SendMessage> messages = new ArrayList<>();
        SendMessage message1 = new SendMessage();
        message1.setChatId(chatId);
        message1.setText("Топ 3 гравці : ");
        messages.add(message1);

        for (int i = 0; i < topusers.size(); i++) {
            User user = topusers.get(i);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(user.getUserName() + ": " + user.getPoints());
            messages.add(message);
            
        }
        return messages;
    }



}
