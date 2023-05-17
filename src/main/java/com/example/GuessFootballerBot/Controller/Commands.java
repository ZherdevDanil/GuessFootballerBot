package com.example.GuessFootballerBot.Controller;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface Commands {
    List<BotCommand> commands = List.of(
            new BotCommand("/start" , "start bot"),
            new BotCommand("/help","game rules"),
            new BotCommand("/possiblefootballer" , "list_of_footballers")
    );


}
