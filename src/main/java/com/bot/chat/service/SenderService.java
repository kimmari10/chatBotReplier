package com.bot.chat.service;

import com.bot.chat.domain.Room;
import com.bot.chat.domain.Sender;
import com.bot.chat.domain.SenderRepository;
import com.bot.chat.dto.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SenderService {

    private final SenderRepository senderRepository;

    public void saveOrUpdate(RequestDto dto) {
        String name = dto.getSender();
        String room = dto.getRoom();
        String groupChatYn = dto.isGroupChat() == true ? "Y" : "N";

        Sender sender = senderRepository.getByRoomAndName(room, name, groupChatYn);

        if (sender == null) {
            Room roomEntity = Room.builder()
                    .name(room)
                    .groupChatYn(groupChatYn)
                    .build();

            Sender senderEntity = Sender.builder()
                    .name(name)
                    .room(roomEntity)
                    .build();

            senderRepository.save(senderEntity);
        }
    }
}
