package com.bot.chat.controllers;

import com.bot.chat.dto.RequestDto;
import com.bot.chat.dto.ResponseDto;
import com.bot.chat.service.CommandService;
import com.bot.chat.service.SenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotController {

    private final String COMMAND_SIGNAL = "!";
    private final CommandService commandService;
    private final SenderService senderService;
//    private final MessageService messageService;

    @GetMapping("/api/bot/reply")
    public ResponseDto proc(RequestDto requestDto) {
        String msg = requestDto.getMsg();

        //Sender, Room, Message 저장
        senderService.saveOrUpdate(requestDto);

        //4. 메세지 처리
        commandService.execCommand(msg);

        //4-1. !로시작하는 커맨드인지 확인
        if(msg.startsWith(COMMAND_SIGNAL)) {

            if(msg.endsWith("날씨")) {
                return commandService.execWeatherCommand(msg);
            } else {
                return commandService.execCommand(msg);
            }
        } else {
            commandService.execCommandContainsKeyword(msg);
            ResponseDto dto = ResponseDto.builder()
                    .title("title")
//                    .content("content")
                    .build();

            return dto;
        }
    }
}
