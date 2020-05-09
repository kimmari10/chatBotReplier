package com.bot.chat.service;

import com.bot.chat.domain.Command;
import com.bot.chat.domain.CommandRepository;
import com.bot.chat.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandService {

    private final CommandRepository commandRepository;

    public boolean isContainCommandList(String msg) {
        boolean isContain = false;
        String cmdKeyword = "";

        //명령어 리스트 조회
        List<Command> commandList = commandRepository.findAll();
        for (Command command : commandList) {
            cmdKeyword = command.getCommand();

            //DB에 저장된 명령어가 메세지에 포함되는지
            if(cmdKeyword.indexOf(msg) > 0) {
                isContain = true;
            }
        }
        return isContain;
    }


    public ResponseDto execCommand(String msg) {
        String title = "";
        String content = "";

        //명령어 유효여부
        if("!도움말".equals(msg)) {
            //TODO 명령어 소개 메세지

        } else if(isContainCommandList(msg)) {
            //TODO !명령어 처리

        }


        //결과 처리
        ResponseDto dto = ResponseDto.builder()
                            .success(true)
                            .title(title)
                            .content(content)
                            .build();

        return dto;
    }

    public ResponseDto execWeatherCommand(String msg) {


        ResponseDto dto = ResponseDto.builder()
                .success(true)
                .title("날씨")
                .content("")
                .build();

        return dto;
    }

    public ResponseDto execCommandContainsKeyword(String msg) {


        ResponseDto dto = ResponseDto.builder()
                .success(true)
                .content("")
                .build();

        return dto;
    }
}
