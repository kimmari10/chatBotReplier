package com.bot.chat.controllers;

import com.bot.chat.dto.command.CommandSaveRequestDto;
import com.bot.chat.dto.command.RequestDto;
import com.bot.chat.dto.command.ResponseDto;
import com.bot.chat.service.InfoService;
import com.bot.chat.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class BotController {

    private final String COMMAND_SIGNAL = "!";
    private final ReplyService replyService;
    private final InfoService infoService;

    @GetMapping("/api/reply")
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

            return replyService.execContainsKeywordCommand(requestDto);
        }
    }

    @PostMapping("/api/command/save")
    public void saveCommand(@RequestBody CommandSaveRequestDto dto) {
        replyService.save(dto);
    }

    @DeleteMapping("/api/command/delete/{id}")
    public void deleteCommand(@PathVariable Long id) {
        replyService.delete(id);
    }


    public boolean isSystemCommand(String msg) {
        return msg.startsWith(COMMAND_SIGNAL);
    }

    public boolean isWeatherCommand(String msg) {
        return isSystemCommand(msg) && msg.endsWith("날씨");
    }

}
