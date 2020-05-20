package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.name=:roomName")
    Room findRoomByName(String roomName);

}
