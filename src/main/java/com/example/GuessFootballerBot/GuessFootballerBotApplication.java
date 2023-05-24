package com.example.GuessFootballerBot;

import com.example.GuessFootballerBot.Controller.BotFunctionality;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class})
public class GuessFootballerBotApplication {

	public static void main(String[] args) {
		//BotFunctionality botFunctionality = new BotFunctionality(configuration);


		SpringApplication.run(GuessFootballerBotApplication.class, args);
	}

}
