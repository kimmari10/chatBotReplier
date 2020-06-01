package com.bot.chat.dto;


import com.bot.chat.domain.Command;
import com.bot.chat.domain.Message;
import com.bot.chat.domain.Room;
import com.bot.chat.domain.Sender;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class ResponseSummaryDto {
    private List<String> rooms;
    private List<String> senders;
    private List<String> messages;

    public ResponseSummaryDto(Room room, Sender sender, Message message) {
        //TODO........
    }
}
