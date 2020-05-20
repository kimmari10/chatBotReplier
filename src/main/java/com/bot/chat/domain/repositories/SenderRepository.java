package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SenderRepository extends JpaRepository<Sender, Long> {

    @Query("SELECT s FROM Sender s INNER JOIN s.room r WHERE r.name=:room AND s.name=:name AND r.groupChatYn=:groupChatYn")
    Sender getByRoomAndName(String room, String name, String groupChatYn);
}
