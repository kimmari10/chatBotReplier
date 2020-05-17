package com.bot.chat.service;

import com.bot.chat.domain.Command;
import com.bot.chat.domain.CommandRepository;
import com.bot.chat.dto.ResponseCommandDto;
import com.bot.chat.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommandService {

    private final CommandRepository commandRepository;

    public boolean isContainCommandList(String msg) {
        log.debug(" >>>> isContainCommandList");

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
        log.debug(" >>>> execCommand");

        String title = "";
        String content = "";

        Command cmd = Command.builder()
                .title("도움말")
                .command("도움말")
                .content("!도움말을 쳐보세요.")
                .signalYn("Y")
                .build();


//        commandRepository.save(cmd);

        //명령어 유효여부
        if("!도움말".equals(msg)) {
            //TODO 명령어 소개 메세지



        } else if(isContainCommandList(msg)) {
            //TODO !명령어 처리

        }


        //결과 처리
        ResponseDto dto = ResponseDto.builder()
                            .title(title)
                            .build();

        return dto;
    }

    public ResponseDto execWeatherCommand(String msg) {
        final String SEP = ",";
        String title = msg.replace("!", "");
        String place = title.replace("날씨", "").trim();

        ResponseDto dto = ResponseDto.builder()
                .title(title)
                .build();

        String nowTemp, upTemp, downTemp, moist, wind, result = "";

        Document weatherDom = null;

        try {
            weatherDom = Jsoup.connect("https://m.search.naver.com/search.naver?query="+place+"날씨").get();

            nowTemp = weatherDom.select(".temperature_text").get(0).text().replace("현재 온도", "").trim();
            upTemp = weatherDom.select(".up_temperature").text();
            downTemp = weatherDom.select(".down_temperature").text();
            moist = weatherDom.select(".type_humidity .figure_result").text();
            wind = weatherDom.select(".type_wind .figure_result").text();

            if("".equals(nowTemp) || "".equals(upTemp) || "".equals(downTemp) || "".equals(moist) || "".equals(wind)) {
                System.out.println("null check");
            } else {
                dto = dto.toBuilder()
                    .success(true)
                    .title(title)
                    .content("현재온도 : "+nowTemp)
                    .content("최고온도 : "+upTemp)
                    .content("최저온도 : "+downTemp)
                    .content("습　　도 : "+moist+"%")
                    .content("바　　람 : "+wind+"m/s")
                    .build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(dto.toString());
            return dto;
        }

    }

    public ResponseDto execCommandContainsKeyword(String msg) {
        //명령어 유무 조회
        ResponseDto dto = ResponseDto.builder().build();

        if(isContainCommandList(msg)) {



            dto = dto.toBuilder()
                    .success(true)
                    .build();
        }


        return dto;
    }

    public List<ResponseCommandDto> getCommandList() {
        return commandRepository.findAll()
                .stream()
                .map(ResponseCommandDto::new)
                .collect(Collectors.toList());
    }
}
