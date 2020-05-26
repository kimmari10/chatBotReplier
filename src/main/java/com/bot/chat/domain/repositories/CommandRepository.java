package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Command;
import com.bot.chat.dto.command.ResponseCommandDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommandRepository extends JpaRepository<Command, Long> {
    @Query("SELECT c FROM Command c WHERE :keyword LIKE CONCAT('%',c.command,'%') ORDER BY c.id DESC")
    ResponseCommandDto findByKeyword(String keyword);
}
