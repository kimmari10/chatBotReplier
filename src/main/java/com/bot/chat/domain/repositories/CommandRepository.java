package com.bot.chat.domain.repositories;

import com.bot.chat.domain.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
}
