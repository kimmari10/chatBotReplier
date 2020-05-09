package com.bot.chat.controllers;

import com.bot.chat.dto.ResponseDto;
import com.bot.chat.service.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotController {

    private final String COMMAND_SIGNAL = "!";
    private final CommandService commandService;

    @GetMapping(value = "/api/bot/{msg}")
    public ResponseDto proc(@PathVariable String msg) {

        //1. !로시작하는 커맨드인지 확인
        if(msg.startsWith(COMMAND_SIGNAL)) {

            if(msg.endsWith("날씨")) {
                return commandService.execWeatherCommand(msg);
            } else {
                return commandService.execCommand(msg);
            }
        } else {
            commandService.execCommandContainsKeyword(msg);
            ResponseDto dto = ResponseDto.builder()
                    .success(true)
                    .title("title")
//                    .content("content")
                    .build();

            return dto;
        }
    }
}
