package com.bot.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class RequestDto {
    private String room;
    private String msg;
    private String sender;
    private boolean isGroupChat;

}

