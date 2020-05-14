package com.bot.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    private String groupChatYn;

    @OneToOne(mappedBy = "room")
    Sender sender;

    @Builder(toBuilder = true)
    public Room(String name, String groupChatYn, Sender sender) {
        this.name = name;
        this.groupChatYn = groupChatYn;
        this.sender = sender;
    }
}
