package com.bot.chat.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Room room;

    @Builder
    public Sender(String name, Room room) {
        this.name = name;
        this.room = room;
    }
}
