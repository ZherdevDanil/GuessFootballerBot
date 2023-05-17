package com.example.GuessFootballerBot.Controller;

import com.example.GuessFootballerBot.Config.BotConfiguration;
import jakarta.validation.constraints.NotNull;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;

@Component
public class BotFunctionality extends TelegramLongPollingBot implements Commands {
    BotConfiguration configuration;

    public BotFunctionality (BotConfiguration configuration){
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

    public void commandReaction(String messageText ,Long chatId , String Username ) {
        switch (messageText) {
            case "/start":
                try {
                    startbot(chatId, Username);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/help":
                try {
                    sendrules(chatId);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/possiblefootballer":
                try {
                    sendDoc(chatId, new File("C:\\GuessFootballerBot\\footballerhelp.txt"), "Список можливих футболістів");
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
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
    public void startbot(Long chatid , String UserName) throws TelegramApiException {
        SendMessage message = new SendMessage();

        message.setChatId(chatid);
        message.setText("Привіт " + UserName + " Я телеграм бот GuessFootballer , і якщо тобі нічим зайнятися, я сподіваюся що ти добре проведеш тут час");
        execute(message);
    }

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



}
