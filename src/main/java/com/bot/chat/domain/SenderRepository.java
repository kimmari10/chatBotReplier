package com.bot.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SenderRepository extends JpaRepository<Sender, Long> {

    @Query("SELECT s FROM Sender s WHERE s.room.name=:room AND s.name=:name AND s.room.groupChatYn=:groupChatYn")
    Sender getByRoomAndName(String room, String name, String groupChatYn);
}
