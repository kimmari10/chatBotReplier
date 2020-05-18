package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
