package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Message;
import com.bot.chat.dto.message.ResponseMessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m INNER JOIN FETCH m.sender s WHERE m.delYn <> 'Y' AND s.name =:senderName ORDER BY m.createdDateTime DESC")
    List<ResponseMessageDto> findAllBySenderTimeDesc(String senderName);

    @Query("SELECT m FROM Message m INNER JOIN FETCH m.sender s WHERE m.delYn <> 'Y' AND s.name =:senderName AND s.room.name =:roomName ORDER BY m.createdDateTime DESC")
    List<ResponseMessageDto> findAllByRoomAndSenderTimeDesc(String roomName, String senderName);
}
