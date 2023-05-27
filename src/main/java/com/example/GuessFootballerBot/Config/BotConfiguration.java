package com.example.GuessFootballerBot.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@Setter
@PropertySource("classpath:application.properties")
public class BotConfiguration {
    @Value("${bot.name}")
    String bot_name;

    @Value("${bot.token}")
    String bot_token;
}
