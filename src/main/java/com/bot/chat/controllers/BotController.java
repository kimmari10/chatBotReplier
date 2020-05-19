package com.bot.chat.controllers;

import com.bot.chat.dto.RequestDto;
import com.bot.chat.dto.ResponseDto;
import com.bot.chat.service.ReplyService;
import com.bot.chat.service.InfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class BotController {

    private final String COMMAND_SIGNAL = "!";
    private final ReplyService replyService;
    private final InfoService infoService;

    @GetMapping("/api/bot/reply")
    public ResponseDto proc(RequestDto requestDto) {
        String msg = requestDto.getMsg();

        //Sender, Room, Message 저장
        infoService.saveOrUpdate(requestDto);

        if(isWeatherCommand(msg)) {
            log.info(" >>>> 날씨명령어");

            return replyService.execWeatherCommand(msg);
        } else if(isSystemCommand(msg)) {
            log.info(" >>>> 시스템명령어");
            
            return replyService.execSystemCommand(msg);
        } else {
            log.info(" >>>> 포함단어 처리");

            replyService.execContainsKeywordCommand(msg);
            ResponseDto dto = ResponseDto.builder()
                    .title("title")
                    .content("")
                    .build();

            return dto;
        }
    }

    public boolean isSystemCommand(String msg) {
        return msg.startsWith(COMMAND_SIGNAL);
    }
    public boolean isWeatherCommand(String msg) {
        return msg.startsWith(COMMAND_SIGNAL) && msg.endsWith("날씨");
    }
}
