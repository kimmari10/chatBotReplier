package com.bot.chat.service;

import com.bot.chat.domain.Command;
import com.bot.chat.domain.repositories.CommandRepository;
import com.bot.chat.dto.RequestDto;
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
public class ReplyService {

    private final CommandRepository commandRepository;

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


    public ResponseDto execSystemCommand(String msg) {
        String title = "";
        String content = "";

        ResponseDto dto = ResponseDto.builder()
                .title("도움말")
                .build();

        if("!명령어".equals(msg)) {
            List<String> commandList = commandRepository.findAll()
                    .stream()
                    .map(Command::getTitle)
                    .collect(Collectors.toList());

            dto = dto.toBuilder()
                    .success(commandList.size() > 0)
                    .contents(commandList)
                    .build();

        } else {
            //TODO !명령어 처리

        }

        return dto;
    }

    public ResponseDto execContainsKeywordCommand(RequestDto requestDto) {
        //명령어 유무 조회
        String msg = requestDto.getMsg();
        String sender = requestDto.getSender();

        ResponseDto dto = ResponseDto.builder().build();
        ResponseCommandDto commandDto = commandRepository.findByKeyword(msg);

        if(commandDto != null) {
            String title = commandDto.getTitle();
            String content = commandDto.getContent();

            if(title.equals("무복")) {
                content = sender + " " + content;
            }

            dto = dto.toBuilder()
                    .success(true)
                    .title(title)
                    .content(content)
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
