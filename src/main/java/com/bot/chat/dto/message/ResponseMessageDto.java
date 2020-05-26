package com.bot.chat.dto.message;


import com.bot.chat.domain.Command;
import com.bot.chat.domain.Message;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;


@Getter
@ToString
public class ResponseMessageDto {
    private String content;
    private String senderName;
    private String sendDate;
    private String sendTime;

    public ResponseMessageDto(Message entity) {
        this.content = entity.getContent();
        this.senderName = entity.getSender().getName();
        this.sendDate = entity.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.sendTime = entity.getCreatedDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
