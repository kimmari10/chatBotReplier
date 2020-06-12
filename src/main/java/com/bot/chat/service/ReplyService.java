package com.bot.chat.service;

import com.bot.chat.domain.Command;
import com.bot.chat.domain.repositories.CommandRepository;
import com.bot.chat.dto.command.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
            log.info(dto.toString());
            return dto;
        }

    }


    public ResponseDto execSystemCommand(String msg) {
        String title = "";
        String content = "";

        ResponseDto dto = ResponseDto.builder()
                .build();

        if("!명령어".equals(msg)) {
            List<String> commandList = commandRepository.findAll()
                    .stream()
                    .map(Command::getCommand)
                    .collect(Collectors.toList());

            dto = dto.toBuilder()
                    .success(commandList.size() > 0)
                    .contents(commandList)
                    .build();

        } else if("!도움말".equals(msg)) {
            dto = dto.toBuilder()
                    .success(true)
                    .title("도움말")
                    .content("!명령어 - 단어 확인")
                    .content("!추가 [단어] [메세지]")
                    .content("!삭제 [단어]")
                    .build();

        } else if (msg.startsWith("!추가 ")) {
            msg = msg.replace("!추가 ", "");
            int sepIdx = msg.indexOf(" ");
            if(sepIdx > 0) {
                String parseCommand = msg.substring(0, sepIdx);
                String parseContent = msg.substring(sepIdx+1);

                Command findCmd = commandRepository.findByCommand(parseCommand);

                if(findCmd == null) {
                    Command saveCmd = Command.builder()
                            .command(parseCommand)
                            .content(parseContent)
                            .signalYn("N")
                            .title(parseCommand)
                            .build();

                    commandRepository.save(saveCmd);
                }

            }
        } else if (msg.startsWith("!삭제")) {
            int sepIdx = msg.indexOf(" ");
            if(sepIdx > 0) {
                String parseCommand = msg.substring(0, sepIdx);

                Command findCmd = commandRepository.findByCommand(parseCommand);

                if (findCmd != null) {
                    commandRepository.delete(findCmd);
                }
            }
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
            String command = commandDto.getCommand();
            String content = commandDto.getContent();

            if(command.equals("무복")) {
                content = sender + " " + content;
            }

            dto = dto.toBuilder()
                    .success(true)
                    .title(command)
                    .content(content)
                    .build();
        }

        return dto;
    }

    public void save(CommandSaveRequestDto dto) {
        Command cmd = Command.builder()
                .title("")
                .command(dto.getCommand())
                .content(dto.getContent())
                .signalYn("N")
                .build();
        commandRepository.save(cmd);
    }

    public void delete(Long id) {
        Optional<Command> cmd = commandRepository.findById(id);
        log.info(cmd.get().toString());
        if(cmd.isPresent()) {
            commandRepository.delete(cmd.get());
        }
    }

    public List<ResponseCommandDto> getCommandList() {
        return commandRepository.findAll()
                .stream()
                .map(ResponseCommandDto::new)
                .collect(Collectors.toList());
    }
}
