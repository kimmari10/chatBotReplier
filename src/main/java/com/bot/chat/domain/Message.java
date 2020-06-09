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

    private String delYn = "N";

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Sender sender;

    @Builder(toBuilder = true)
    public Message(String content, Sender sender) {
        this.content = content;
        this.sender = sender;
    }
}
