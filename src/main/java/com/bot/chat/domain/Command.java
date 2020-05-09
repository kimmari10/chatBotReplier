package com.bot.chat.domain;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nullable
    private String title;

    @Nullable
    private String command;

    @Nullable
    private String content;

    @Nullable
    private String signalYn;

    @Builder
    public Command(String title, String command, String content, String signalYn) {
        this.title = title;
        this.command =  command;
        this.content = content;
        this.signalYn = signalYn;
    }
}
