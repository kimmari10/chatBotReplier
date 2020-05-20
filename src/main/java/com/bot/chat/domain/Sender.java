package com.bot.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

    @Builder
    public Sender(String name, Room room, List<Message> messages) {
        this.name = name;
        this.room = room;
        this.messages = messages;
    }
}
