package com.bot.chat.service;

import com.bot.chat.domain.Message;
import com.bot.chat.domain.repositories.MessageRepository;
import com.bot.chat.dto.message.ResponseMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    public List<ResponseMessageDto> getMessageList() {
        return messageRepository.findAll()
                .stream()
                .map(ResponseMessageDto::new)
                .collect(Collectors.toList());
    }
}
