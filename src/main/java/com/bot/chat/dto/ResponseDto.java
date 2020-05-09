package com.bot.chat.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class ResponseDto {
    private boolean success;
    private String title;
    private String content;
    private boolean isCmdSignal;

    @Builder
    public ResponseDto(boolean success, String title, String content) {
        this.success = success;
        this.title = title;
        this.content = content;
    }
}
