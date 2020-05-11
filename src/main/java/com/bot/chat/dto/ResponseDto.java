package com.bot.chat.dto;


import lombok.*;

import java.util.List;


@Builder(toBuilder = true)
@Getter
@ToString
public class ResponseDto {
    @Builder.Default private boolean success = false;
    private String title;
    @Singular private List<String> contents;
}
