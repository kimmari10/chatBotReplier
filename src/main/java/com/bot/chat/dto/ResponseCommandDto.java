package com.bot.chat.dto;


import com.bot.chat.domain.Command;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class ResponseCommandDto {
    private String command;
    private String content;

    public ResponseCommandDto(Command entity) {
        this.command = entity.getCommand();
        this.content = entity.getContent();
    }
}
