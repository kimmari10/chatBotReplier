package com.bot.chat.dto;


import com.bot.chat.domain.Command;
import com.bot.chat.domain.Message;
import com.bot.chat.domain.Room;
import com.bot.chat.domain.Sender;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@Builder
@ToString
public class ResponseSummaryDto {
    private List<Room> rooms;
    private List<Sender> senders;
    private List<Message> messages;
    private int roomCnt;
    private int senderCnt;
    private int messageCnt;

    public ResponseSummaryDto(List<Room> rooms, List<Sender> senders, List<Message> messages) {
        this.rooms = rooms;
        this.senders = senders;
        this.messages = messages;
        this.roomCnt = rooms.size();
        this.senderCnt = senders.size();
        this.messageCnt = messages.size();
    }
}
