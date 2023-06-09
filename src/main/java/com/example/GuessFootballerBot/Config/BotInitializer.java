package com.example.GuessFootballerBot.Config;

import com.example.GuessFootballerBot.Controller.View.BotFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Клас ініціалізації бота
 */
@Component
public class BotInitializer {

    @Autowired
    BotFunctionality bot;

    @EventListener({ContextRefreshedEvent.class})
    public void initbot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot((LongPollingBot) bot);
        } catch (TelegramApiException e) {
            System.out.println("Помилка" + e.getMessage());
        }
    }
}
