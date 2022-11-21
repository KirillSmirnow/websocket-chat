package wc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(columnList = "chat_id, user_id", unique = true),
        @Index(columnList = "chat_id"),
        @Index(columnList = "user_id"),
})
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class ChatMember {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Chat chat;

    @ManyToOne(optional = false)
    private User user;

    public ChatMember(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
    }
}
