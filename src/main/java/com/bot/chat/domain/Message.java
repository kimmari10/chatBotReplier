package com.bot.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    Sender sender;

    @Builder(toBuilder = true)
    public Message(String content, Sender sender) {
        this.content = content;
        this.sender = sender;
    }
}
