package com.bot.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:/jdbc.properties"})
public class ChatBotReplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBotReplierApplication.class, args);
    }

}
