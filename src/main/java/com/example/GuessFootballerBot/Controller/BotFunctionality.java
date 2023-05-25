package com.example.GuessFootballerBot.Controller;
import com.example.GuessFootballerBot.Config.BotConfiguration;
import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import com.example.GuessFootballerBot.Model.Service.FootballerService;
import com.example.GuessFootballerBot.Model.Service.UserService;
import com.example.GuessFootballerBot.Model.User;
import com.example.GuessFootballerBot.Model.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class BotFunctionality extends TelegramLongPollingBot implements Commands {
    @Autowired
    BotConfiguration configuration;
    @Autowired
    FootballerService footballerService;
    @Autowired
    UserService userService;
    @Autowired
    FootballerFunctionality footballerFunctionality;

    //private Footballer currentFootballer;
    //String currentFootballerFullName = getFootballerFullName(chatId , currentFootballer);
    private int points = 50;


    private static final int FOOTBALLERS_COUNT = 51;

    public BotFunctionality (BotConfiguration configuration) throws IOException {
        this.configuration = configuration;
        try {
            this.execute(new SetMyCommands(commands , new BotCommandScopeDefault(),null ));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public String getBotUsername() {
        return configuration.getBot_name();
    }

    @Override
    public String getBotToken() {
        return configuration.getBot_token();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage()){
            Long chatId = update.getMessage().getChatId();
            String Username = update.getMessage().getFrom().getFirstName();


            if(update.getMessage().hasText()){
                String messageText = update.getMessage().getText();
                commandReaction(messageText , chatId , Username);
            }
        }else if (update.hasCallbackQuery()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String Username = update.getMessage().getFrom().getFirstName();
            commandReaction(messageText , chatId , Username);

        }
    }



    public void commandReaction(String messageText , Long chatId , String Username ) {
        if (messageText.equals("/start")) {
            try {
                import_json_in_db();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            startkeyboard(chatId, hellophrase(Username));
        } else if (messageText.equals("/possiblefootballer")) {
                sendDoc(chatId, new File("C:\\GuessFootballerBot\\footballerhelp.txt"), "Список можливих футболістів");
        } else if (messageText.equals("/my_data") || messageText.equals("my_data")) {
            infoUser(chatId, Username);
        } else if (messageText.equals("/help") || messageText.equals("help")) {
                sendrules(chatId);
        } else if (messageText.equals("/play") || messageText.equals("play")) {
            footballerFunctionality.getFootballer();
            saveUser(chatId, Username, 0);
            startplaykeyboard(chatId, "Оберіть, що перше ви хочете вивести");

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("1.Позиція гравця\n" +
                    "2.Чи грає він досі\n" +
                    "3.Національність\n" +
                    "4.Ім'я\n" +
                    "5.Прізвище\n" +
                    "6.Всі клуби\n" +
                    "7.Клуб\n" +
                    "8.Повернутися назад\n");
            executeSendMessege(message);
            /*try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }*/
        } else if (messageText.equals("/Rating") || messageText.equals("Rating")) {
            getTopPlayers(chatId);
        } else if (messageText.equals("/1") || messageText.equals("1")) {
            executeSendMessege(footballerFunctionality.getFootballerPosition(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/2") || messageText.equals("2")) {
            executeSendMessege(footballerFunctionality.getFootballerStillPlay(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/3") || messageText.equals("3")) {
            executeSendMessege(footballerFunctionality.getFootballerCountry(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/4") || messageText.equals("4")) {
            executeSendMessege(footballerFunctionality.getFootballerName(chatId, footballerFunctionality.currentFootballer));
            points -= 20;
        } else if (messageText.equals("/5") || messageText.equals("5")) {
            executeSendMessege(footballerFunctionality.getFootballerSurname(chatId, footballerFunctionality.currentFootballer));
            points -= 20;
        } else if (messageText.equals("/6") || messageText.equals("6")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs(chatId, footballerFunctionality.currentFootballer));
            points -= 15;
        } else if (messageText.equals("/7") || messageText.equals("7")) {
            clubsKeyboard(chatId, footballerFunctionality.currentFootballer);

        } else if (messageText.equals("/8") || messageText.equals("8")) {
            startkeyboard(chatId, "Оберіть наступну вашу дію");
        } else if (messageText.equals("/Клуб 1") || messageText.equals("Клуб 1")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs1(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 2") || messageText.equals("Клуб 2")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs2(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 3") || messageText.equals("Клуб 3")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs3(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 4") || messageText.equals("Клуб 4")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs4(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 5") || messageText.equals("Клуб 5")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs5(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 6") || messageText.equals("Клуб 6")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs6(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 7") || messageText.equals("Клуб 7")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs7(chatId, footballerFunctionality.currentFootballer));
            points -= 5;
        } else if (messageText.equals("/Клуб 8") || messageText.equals("Клуб 8")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs8(chatId, footballerFunctionality.currentFootballer));
            points -= 5;

        } else if (messageText.equals(footballerFunctionality.getFootballerFullName(footballerFunctionality.currentFootballer)))/*getFootballerFullName(currentFootballer)))*/ {
            if (points < 0) {
                points = 0;
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Поздравляю ви вгадали!!!" + " Гравця " + footballerFunctionality.getFootballerFullName(footballerFunctionality.currentFootballer) + " відгадано\n" +
                        "Вам зараховано " + points + " очок");
                updateUser(chatId, Username, points);
                /*
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }*/
                executeSendMessege(message);
            } else {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Поздравляю ви вгадали!!!" + " Гравця " + footballerFunctionality.getFootballerFullName(footballerFunctionality.currentFootballer) + " відгадано\n" +
                        "Вам зараховано " + points + " очок");
                updateUser(chatId, Username, points);
                executeSendMessege(message);
                /*
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }*/
            }
            points = 50;
            startkeyboard(chatId, "Оберіть наступну вашу дію");

        } else if (messageText.equals("/Повернутися") || messageText.equals("Повернутися")) {
            startplaykeyboard(chatId, "Оберіть, що ви хочете вивести");
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("1.Позиція гравця\n" +
                    "2.Чи грає він досі\n" +
                    "3.Національність\n" +
                    "4.Ім'я\n" +
                    "5.Прізвище\n" +
                    "6.Всі клуби\n" +
                    "7.Клуб\n");
            executeSendMessege(message);
            /*
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }*/
        } else {
            defaultmessage(chatId);
        }
    }

    public String hellophrase(String UserName){
        return "Привіт " + UserName + " Я телеграм бот GuessFootballer , і якщо тобі нічим зайнятися, я сподіваюся ти добре проведеш тут час";
    }


    public void defaultmessage(Long chatId ) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Не розумію що ви хочете від мене");
        executeSendMessege(message);
        //execute(message);
    }

    public void sendDoc(Long chatid ,File file , String caption) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatid);
        sendDocument.setCaption(caption);
        sendDocument.setDocument(new InputFile(file));

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendrules(Long chatid){
        SendMessage message = new SendMessage();
        message.setChatId(chatid);
        message.setText("Правила гри 'Вгадай футболіста' доволі прості\n" +
                "1.Як не дивно головна ваша мета це вгадати заданого ботом футболіста\n" +
                "2.Після натискання або написання в чат команди /play ви отримаєте можливість обрати, які перші дані про футболіста ви хочете вивести, це може бути або перший клуб,або національність,або статус або позиція гравця,також є можливість вивести одразу всі клуби гравця, або роботи це по одному,натискаючи на кнопку наступний клуб\n" +
                "3.Після того як ви вивели початкові значення вам нараховується 30 очок, звичайно за умови, що ви не вивели всі кліби, в такому випадку з вас одразу зніметься 20 очок\n" +
                "4.Кожне наступне відкривання клуба буде коштувати вам 2 балів, відкриття даних про позицію, національність,та статус гравця(грає він, чи вже закінчив кар'єру\n" +
                "5.Також можна вивести або ім'я гравця, або його прізвище,кожна з можливостей буде коштувати вам 25 балів\n" +
                "6.Якщо ви не знаєте що за футболіста вам загадали ви можете нажати на кнопку здатися, і тоді вам буде нараховано 5 очок\n" +
                "7.Правильною відповіддю буде важатися лише коректно написане прізвище та ім'я з великою букви, як правильно писати прізвище та ім'я футболіста ви можете подивитися в файлі footballerhelp.txt , це файл можна отримати задяки цій команді /possiblefootballer\n" +
                "8.Інофрмація про кількість гравців що ви вгадали, та кількість балів що ви за них отримали буде збережена\n" +
                "\n" +
                "Приємної гри!!!");

        executeSendMessege(message);
            //execute(message);
    }

    public void startkeyboard(Long chatId , String MessageText ){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(MessageText);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("help");
        row.add("play");
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("my_data");
        row.add("Rating");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);
        executeSendMessege(message);
        /*try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/
    }
    public void startplaykeyboard(Long chatId , String messageText ){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("1");
        row.add("2");
        row.add("3");
        row.add("4");

        keyboardRows.add(row);

        row = new KeyboardRow();


        row.add("5");
        row.add("6");
        row.add("7");
        row.add("8");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);
        executeSendMessege(message);
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/
    }
    public void updateUser(Long chatId, String Username , Integer points){
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
    public void infoUser(Long chatId , String userName){
        if (userService.existsByChatId(chatId)) {
            User user = userService.findByChatId(chatId);
            String currentUserName = user.getUserName();
            int currentUserPoints = user.getPoints();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ім'я користувача : " + currentUserName + " \nКількість очок : " + currentUserPoints);
            executeSendMessege(message);
            /*
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }*/
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
            executeSendMessege(message);
            /*
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }*/
        }
    }
    public void clubsKeyboard(Long chatId, Footballer currentFootballer) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Оберіть клуб:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        int numberOfClubs = getNumberOfRow(currentFootballer);
        // Генерація кнопок в залежності від кількості клубів
        for (int i = 1; i <= numberOfClubs ; i++) {
                row.add("Клуб " + i);
        }
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("Повернутися");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeSendMessege(message);
        /*
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/
    }

    private int getNumberOfRow(Footballer footballer) {
        int number_of_row = 0;
        if (!footballer.getClubs1().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs2().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs3().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs4().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs5().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs6().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs7().isEmpty()){
            number_of_row++;
        }
        if (!footballer.getClubs8().isEmpty()){
            number_of_row++;
        }
        return number_of_row;
    }

    public void getTopPlayers(Long chatId ){
        List<User> topusers = userService.getTopUsers();
        SendMessage message = new SendMessage();
        SendMessage message1 = new SendMessage();
        message1.setChatId(chatId);
        message.setChatId(chatId);
        message1.setText("Топ 3 гравці : ");
        executeSendMessege(message1);
        for (User user:topusers) {
            message.setText(user.getUserName() + ": " + user.getPoints());
            executeSendMessege(message);

        }

    }

    public void executeSendMessege(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void import_json_in_db() throws IOException {
        if (footballerService.count() == 0){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Footballer> allfootballers = objectMapper.readValue(new File("C:\\GuessFootballerBot\\footballer_data.json"),
            typeFactory.constructCollectionType(List.class, Footballer.class));
            footballerService.saveAll(allfootballers);
        }

    }

}



