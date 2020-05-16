package com.bot.chat.service;

import com.bot.chat.domain.*;
import com.bot.chat.dto.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SenderService {

    private final SenderRepository senderRepository;
    private final MessageRepository messageRepository;

    public void saveOrUpdate(RequestDto dto) {
        String name = dto.getSender();
        String room = dto.getRoom();
        String msg = dto.getMsg();
        String groupChatYn = dto.isGroupChat() == true ? "Y" : "N";

        Sender senderEntity = senderRepository.getByRoomAndName(room, name, groupChatYn);

        if (senderEntity == null) {
            Room roomEntity = Room.builder()
                    .name(room)
                    .groupChatYn(groupChatYn)
                    .build();

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
}
