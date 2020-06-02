package com.bot.chat.service;

import com.bot.chat.domain.Message;
import com.bot.chat.domain.Room;
import com.bot.chat.domain.Sender;
import com.bot.chat.domain.repositories.MessageRepository;
import com.bot.chat.domain.repositories.RoomRepository;
import com.bot.chat.domain.repositories.SenderRepository;
import com.bot.chat.dto.ResponseSummaryDto;
import com.bot.chat.dto.command.RequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class InfoService {

    private final RoomRepository roomRepository;
    private final SenderRepository senderRepository;
    private final MessageRepository messageRepository;

    public void saveOrUpdate(RequestDto dto) {
        String name = dto.getSender();
        String room = dto.getRoom();
        String msg = dto.getMsg();
        String groupChatYn = dto.isGroupChat() == true ? "Y" : "N";

        Room roomEntity = roomRepository.findRoomByName(room);
        Sender senderEntity = senderRepository.getByRoomAndName(room, name, groupChatYn);

        if (roomEntity == null) {
            roomEntity = Room.builder()
                    .name(room)
                    .groupChatYn(groupChatYn)
                    .build();

            roomRepository.save(roomEntity);
        }

        if (senderEntity == null) {
            senderEntity = Sender.builder()
                    .name(name)
                    .room(roomEntity)
                    .build();

            senderRepository.save(senderEntity);
        }

        Message messageEntity = Message.builder()
                .content(msg)
                .sender(senderEntity)
                .build();

        messageRepository.save(messageEntity);
    }

    public List<ResponseSummaryDto> getSummary() {
        ResponseSummaryDto.builder()
                .messages(messageRepository.findAll())
                .senders(senderRepository.findAll())
                .rooms(roomRepository.findAll())
                .build();

        return null;
    }
}
