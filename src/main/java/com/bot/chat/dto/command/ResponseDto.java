package com.bot.chat.dto.command;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;


@Builder(toBuilder = true)
@Getter
@ToString
public class ResponseDto {
    @Builder.Default private boolean success = false;
    private String title;
    @Singular private List<String> contents;
}
