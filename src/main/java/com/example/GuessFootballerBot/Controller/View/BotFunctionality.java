package com.example.GuessFootballerBot.Controller.View;

import com.example.GuessFootballerBot.Config.BotConfiguration;
//import com.example.GuessFootballerBot.Controller.Commands;
import com.example.GuessFootballerBot.Controller.FootballerFunctionality;
import com.example.GuessFootballerBot.Controller.UserFunctionality;
import com.example.GuessFootballerBot.Model.Footballer;
import com.example.GuessFootballerBot.Model.Service.UserFootballerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Клас який бачить, який запит йому направив користувач,
 * клас виводить інформацію з бд, оброблену контроллерам, згідно з запитом користувача
 */
@Component
public class BotFunctionality extends TelegramLongPollingBot {
    private final BotConfiguration configuration;
    private final FootballerFunctionality footballerFunctionality;
    private final UserFunctionality userFunctionality;
    private final UserFootballerService userFootballerService;

    private int[] counterForButtons = new int[6];

    private int[] counterForClubButtons = new int[8];

    private int numberOfClubsCurrentPlayer;
    List<BotCommand> commands = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "game rules"),
            new BotCommand("/possiblefootballer", "list_of_footballers")
    );


    private int points = 50;

    @Autowired
    public BotFunctionality(BotConfiguration configuration, FootballerFunctionality footballerFunctionality, UserFunctionality userFunctionality, UserFootballerService userFootballerService) throws IOException {
        this.configuration = configuration;
        this.footballerFunctionality = footballerFunctionality;
        this.userFunctionality = userFunctionality;
        this.userFootballerService = userFootballerService;
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
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

    /**
     * метод що перевіряє вхідні дані до бота, та чи може він їх обробити
     * @param update
     */
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            String Username = update.getMessage().getFrom().getFirstName();


            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                commandReaction(messageText, chatId, Username);
            }
        } else if (update.hasCallbackQuery()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String Username = update.getMessage().getFrom().getFirstName();
            commandReaction(messageText, chatId, Username);

        }
    }


    /**
     * Метод який повертає інформацію відповідно до запиту користувача
     * @param messageText
     * @param chatId
     * @param Username
     */
    public void commandReaction(String messageText, Long chatId, String Username) {
        if (messageText.equals("/start")) {
            userFunctionality.saveUser(chatId, Username, 3);
            try {
                import_json_in_db();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            startkeyboard(chatId, hellophrase(Username));
        } else if (messageText.equals("/possiblefootballer")) {
            sendDoc(chatId, new File("C:\\GuessFootballerBot\\footballerhelp.pdf"), "Список можливих футболістів");
        } else if (messageText.equals("/my_data") || messageText.equals("my_data")) {
            executeSendMessege(userFunctionality.infoUser(chatId, Username));
        } else if (messageText.equals("/help") || messageText.equals("help")) {
            sendrules(chatId);
        } else if (messageText.equals("/play") || messageText.equals("play")) {
            System.out.println(Arrays.toString(counterForButtons));

            footballerFunctionality.getFootballer();
            footballerFunctionality.setCurrentFootballer(footballerRepeatability(chatId));
            numberOfClubsCurrentPlayer = getNumberOfRow(footballerFunctionality.getCurrentFootballer());
            setZeroForCounters(counterForButtons);
            setZeroForCounters(counterForClubButtons);

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
        } else if (messageText.equals("/Rating") || messageText.equals("Rating")) {
            for (int i = 0; i < footballerFunctionality.getTopPlayers(chatId).size(); i++) {
                executeSendMessege(footballerFunctionality.getTopPlayers(chatId).get(i));
            }
        } else if (messageText.equals("/1") || messageText.equals("1")) {
            executeSendMessege(footballerFunctionality.getFootballerPosition(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(0, 10);

        } else if (messageText.equals("/2") || messageText.equals("2")) {
            executeSendMessege(footballerFunctionality.getFootballerStillPlay(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(1, 10);
        } else if (messageText.equals("/3") || messageText.equals("3")) {
            executeSendMessege(footballerFunctionality.getFootballerCountry(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(2, 10);
        } else if (messageText.equals("/4") || messageText.equals("4")) {
            executeSendMessege(footballerFunctionality.getFootballerName(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(3, 40);
        } else if (messageText.equals("/5") || messageText.equals("5")) {
            executeSendMessege(footballerFunctionality.getFootballerSurname(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(4, 45);
        } else if (messageText.equals("/6") || messageText.equals("6")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatButtons(5, numberOfClubsCurrentPlayer*5);
        } else if (messageText.equals("/7") || messageText.equals("7")) {
            clubsKeyboard(chatId, footballerFunctionality.getCurrentFootballer());
        } else if (messageText.equals("/8") || messageText.equals("8")) {
            startkeyboard(chatId, "Оберіть наступну вашу дію");
        } else if (messageText.equals("/Клуб 1") || messageText.equals("Клуб 1")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs1(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(0, 5);
        } else if (messageText.equals("/Клуб 2") || messageText.equals("Клуб 2")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs2(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(1, 5);
        } else if (messageText.equals("/Клуб 3") || messageText.equals("Клуб 3")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs3(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(2, 5);
        } else if (messageText.equals("/Клуб 4") || messageText.equals("Клуб 4")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs4(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(3, 5);
        } else if (messageText.equals("/Клуб 5") || messageText.equals("Клуб 5")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs5(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(4, 5);
        } else if (messageText.equals("/Клуб 6") || messageText.equals("Клуб 6")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs6(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(5, 5);
        } else if (messageText.equals("/Клуб 7") || messageText.equals("Клуб 7")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs7(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(6, 5);
        } else if (messageText.equals("/Клуб 8") || messageText.equals("Клуб 8")) {
            executeSendMessege(footballerFunctionality.getFootballerClubs8(chatId, footballerFunctionality.getCurrentFootballer()));
            repeatClubButtons(7, 5);

        } else if (messageText.equals(footballerFunctionality.getFootballerFullName(footballerFunctionality.getCurrentFootballer()))) {
            if (points < 0) {
                points = 0;
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Поздравляю ви вгадали!!!" + " Гравця " + footballerFunctionality.getFootballerFullName(footballerFunctionality.getCurrentFootballer()) + " відгадано\n" +
                        "Вам зараховано " + points + " очок");
                userFootballerService.addUserFootballer(chatId, footballerFunctionality.getCurrentFootballer().getId());
                userFunctionality.updateUser(chatId, points);

                executeSendMessege(message);
            } else {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Поздравляю ви вгадали!!!" + " Гравця " + footballerFunctionality.getFootballerFullName(footballerFunctionality.getCurrentFootballer()) + " відгадано\n" +
                        "Вам зараховано " + points + " очок");
                userFootballerService.addUserFootballer(chatId, footballerFunctionality.getCurrentFootballer().getId());
                userFunctionality.updateUser(chatId, points);
                executeSendMessege(message);
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
                    "7.Клуб\n" +
                    "8.Повернутися");
            executeSendMessege(message);
        } else {
            defaultmessage(chatId);
        }
    }

    /**
     * метод повертає фразу привітання
     * @param UserName
     * @return
     */
    public String hellophrase(String UserName) {
        return "Привіт " + UserName + " Я телеграм бот GuessFootballer , і якщо тобі нічим зайнятися, я сподіваюся ти добре проведеш тут час";
    }

    /**
     * метод відправляє message коли не розпізнає запит який надіслав користувач
     * @param chatId
     */
    public void defaultmessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Не розумію");
        executeSendMessege(message);
    }

    /**
     * метод відправляє документ з можливими футболістами
     * @param chatid
     * @param file
     * @param caption
     */
    public void sendDoc(Long chatid, File file, String caption) {
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

    /**
     * метод відправляє правила гри
     * @param chatid
     */
    public void sendrules(Long chatid) {
        SendMessage message = new SendMessage();
        message.setChatId(chatid);
        message.setText("Правила гри 'Вгадай футболіста' доволі прості\n" +
                "1.Як не дивно головна ваша мета це вгадати заданого ботом футболіста\n" +
                "2.Після натискання або написання в чат команди /play ви отримаєте можливість обрати, які перші дані про футболіста ви хочете вивести, це може бути або перший клуб,або національність,або статус або позиція гравця,також є можливість вивести одразу всі клуби гравця, або роботи це по одному\n" +
                "3.На самому початку гри вам нараховується 50 очок, за кожне відкриття якоїсь інформації у вас будуть зніматися очки,і ті очки які залишилися будуть вам додані\n" +
                "4.Відкрити ім'я буде коштувати вам 40 балів, відкрити прізвище буде коштувати 45 балів\n" +
                "5.Такі параметри як позиція,чи грає гравець досі, національність будуть коштувати по 10 балів\n" +
                "6.За вивід кожного клубу буде зніматися по 5, очок, якщо ж ви хочете вивести всі клуби разом то це буде коштувати кількість_клубів*5\n" +
                "7.Правильною відповіддю буде вважатися лише коректно написане прізвище та ім'я з великою букви, як правильно писати прізвище та ім'я футболіста ви можете подивитися в файлі footballerhelp.pdf , це файл можна отримати завдяки цій команді /possiblefootballer\n" +
                "8.Якщо ви не хочете відгадувати футболіста який вам випав, ви можете натистути на клавішу повернутися і потім знову нажати на play, в такому випадку буде згенерований новий гравець\n" +
                "9.Також ви маєте можливість подивитися на свою кількість очок, а також подивитися топ 3 гравці" +
                "\n" +
                "\n" +
                "Приємної гри!!!");

        executeSendMessege(message);
    }

    /**
     * Метод створює початкову ReplyKeyboard
     * @param chatId
     * @param MessageText
     */
    public void startkeyboard(Long chatId, String MessageText) {
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
    }

    /**
     * метод створює ReplyKeyboard з кнопками які позначують, що саме користувач може вивести
     * @param chatId
     * @param messageText
     */
    public void startplaykeyboard(Long chatId, String messageText) {
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
    }

    /**
     * метод створює ReplyKeyboard з кількістю кнопок яка дорівнює кількості клубам гравця(завдяки методу getNumberOfRow)
     * @param chatId
     * @param currentFootballer
     */
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
        for (int i = 1; i <= numberOfClubs; i++) {
            row.add("Клуб " + i);
        }
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("Повернутися");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeSendMessege(message);
    }

    /**
     * метод перевіряє в скількох клубах грав футболіст
     * @param footballer
     * @return
     */
    private int getNumberOfRow(Footballer footballer) {
        int number_of_row = 0;
        if (!footballer.getClubs1().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs2().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs3().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs4().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs5().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs6().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs7().isEmpty()) {
            number_of_row++;
        }
        if (!footballer.getClubs8().isEmpty()) {
            number_of_row++;
        }

        return number_of_row;
    }


    /**
     * Метод який перевіряє id currentFootballer зі списком  footballerId що є в бд userFotballer
     *  Якщо вони рівні то генерується новий футболіст з бд FootballerBd допоки id будуть різні
     *  Якщо кількість відгаданих футболістів у користувача, тобто довжина списку з id відгаданих футболістів та футболістів з
     *  з FootballerBd рівні, то поля в таблиці UserFootballer видаляються і користувач може знову відгадувати тих футболістів
     * що вже відгадав
     * @param chatId
     * @return
     */
    public Footballer footballerRepeatability(Long chatId) {
        List<Integer> footballerIds = userFootballerService.findUserFootballersByChatId(chatId);
        Footballer footballer = footballerFunctionality.getCurrentFootballer();
        if (footballerIds.size() == footballerFunctionality.getFOOTBALLERS_COUNT()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Поздравляю!!! Ви вгадали всіх футболістів яки були в базі даних.\n" +
                    "Відтепер вам зустрічатимуться гравці яких ви вже відгадували");
            executeSendMessege(message);
            userFootballerService.deleteRowsByChatId(chatId);

        } else {
            while (footballerIds.contains(footballer.getId())) {
                System.out.println("Випав гравець який вже є в базі данних");
                footballerFunctionality.getFootballer();
                footballer = footballerFunctionality.getCurrentFootballer();
            }
        }
        return footballer;
    }

    /**
     *  метод перевіряє чи нажималася одна і та сама кнопка декілька разів, якщо кнопка нажата вперше,
     *   то з користувача знімаютсья очки, якщо ні, то очки не знімаються
     *
     * @param i
     * @param countOfPoint
     */
    public void repeatButtons(int i, int countOfPoint) {
        if (counterForButtons[i] == 0) {
            points = points - countOfPoint;
            counterForButtons[i] += 1;
        }
    }
    /**
     *  метод перевіряє чи нажмималася одна і та сама кнопка декілька разів в розділі Клуб, якщо кнопка нажата вперше,
     * то з користувача знімаютсья очки, якщо ні, то очки не змінаються
     *
     * @param i
     * @param countOfPoint
     */
    public void repeatClubButtons(int i, int countOfPoint) {
        if (counterForClubButtons[i] == 0) {
            points = points - countOfPoint;
            counterForClubButtons[i] += 1;
        }
    }

    public void setZeroForCounters(int[] counterArray) {
        for (int i = 0; i < counterArray.length; i++) {
            counterArray[i] = 0;
        }

    }

    /**
     * Метод для виводу тексту з екземпляру типу SendMessage
     */

    public void executeSendMessege(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * метод для  імпорту даних із файлу footballer_data.json до таблиці FootballerDb
     */


    public void import_json_in_db() throws IOException {
        if (footballerFunctionality.getFootballerService().count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Footballer> allfootballers = objectMapper.readValue(new File("C:\\GuessFootballerBot\\footballer_data.json"),
                    typeFactory.constructCollectionType(List.class, Footballer.class));
            footballerFunctionality.getFootballerService().saveAll(allfootballers);
        }

    }

}



