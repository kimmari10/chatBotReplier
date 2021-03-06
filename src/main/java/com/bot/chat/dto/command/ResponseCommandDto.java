package com.bot.chat.dto.command;


import com.bot.chat.domain.Command;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class ResponseCommandDto {
    private long id;
    private String title;
    private String command;
    private String content;
    private String signalYn;

    public ResponseCommandDto(Command entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.command = entity.getCommand();
        this.content = entity.getContent();
        this.signalYn = entity.getSignalYn();
    }

    public Command toEntity() {
        return Command.builder()
                .title(title)
                .command(command)
                .content(content)
                .signalYn(signalYn)
                .build();
    }

}
