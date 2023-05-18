package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Config.BotConfiguration;
import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.FootballerRepository;
import com.example.GuessFootballerBot.Model.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BotFunctionality extends TelegramLongPollingBot implements Commands {
    BotConfiguration configuration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FootballerRepository footballerRepository;

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


    @SneakyThrows
    public void commandReaction(String messageText , Long chatId , String Username )  {
        switch (messageText) {
            case "/start":
                    import_json_in_db();
                    //startbot(chatId, Username);
                    startkeyboard(chatId , hellophrase(Username));
                break;
            case "/possiblefootballer":
                try {
                    sendDoc(chatId, new File("C:\\GuessFootballerBot\\footballerhelp.txt"), "Список можливих футболістів");
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;



            case "/help":
            case "help" :
                try {
                    sendrules(chatId);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/play":
            case "play":

                startplaykeyboard(chatId , "Оберіть, що перше ви хочете вивести");

                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("1.Позиція гравця\n" +
                        "2.Чи грає він досі\n" +
                        "3.Національність\n" +
                        "4.Ім'я\n" +
                        "5.Прізвище\n" +
                        "6.Всі клуби\n" +
                        "7.Перший клуб\n");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "1":
            case "/1":
                break;
            case "2":
            case "/2":
                break;
            case "3":
            case "/3":
                break;
            case "4":
            case "/4":
                break;
            case "5":
            case "/5":
                break;
            case "6":
            case "/6":
                break;
            case "7":
            case "/7":
                break;
            case "8":
            case "/8":
                break;
            default:
                try {
                    defaultmessage(chatId);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("Не знаю що написано");
                break;


        }
    }

    public String hellophrase(String UserName){
        return "Привіт " + UserName + " Я телеграм бот GuessFootballer , і якщо тобі нічим зайнятися, я сподіваюся ти добре проведеш тут час";
    }

    /*
    public void startbot(Long chatid , String UserName) throws TelegramApiException {
        SendMessage message = new SendMessage();

        message.setChatId(chatid);
        message.setText("Привіт " + UserName + " Я телеграм бот GuessFootballer , і якщо тобі нічим зайнятися, я сподіваюся що ти добре проведеш тут час");
        execute(message);
    }

     */

    public void defaultmessage(Long chatid ) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatid);
        message.setText("Не розумію що ви хочете від мене");
        execute(message);
    }

    public void sendDoc(Long chatid ,File file , String caption) throws TelegramApiException {
        SendDocument sendDocumentreq = new SendDocument();
        sendDocumentreq.setChatId(chatid);
        sendDocumentreq.setCaption(caption);
        sendDocumentreq.setDocument(new InputFile(file));
        execute(sendDocumentreq);

    }

    public void sendrules(Long chatid) throws TelegramApiException {
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

            execute(message);
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
/*
        row = new KeyboardRow();

        row.add("register");
        row.add("check my data");
        row.add("delete my data");

        keyboardRows.add(row);
*/
        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


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

        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("4");
        row.add("5");
        row.add("6");
        row.add("7");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void import_json_in_db() throws IOException {
        if (footballerRepository.count() == 0){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Footballer> allfootballers = objectMapper.readValue(new File("C:\\GuessFootballerBot\\footballer_data.json"),
            typeFactory.constructCollectionType(List.class, Footballer.class));
            footballerRepository.saveAll(allfootballers);
        }

    }

}



