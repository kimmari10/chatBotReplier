package com.bot.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    private String groupChatYn;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @Singular("sender")
    private List<Sender> senders = new ArrayList<>();

    @Builder(toBuilder = true)
    public Room(String name, String groupChatYn, List<Sender> senders) {
        this.name = name;
        this.groupChatYn = groupChatYn;
        this.senders = senders;
    }
}
