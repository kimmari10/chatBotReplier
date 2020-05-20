package com.bot.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Message extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    private Sender sender;

    @Builder(toBuilder = true)
    public Message(String content, Sender sender) {
        this.content = content;
        this.sender = sender;
    }
}
