package wc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(columnList = "chat_member_id"),
})
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Message {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private ChatMember chatMember;

    @NotNull
    private LocalDateTime sentAt;

    @NotNull
    @NotBlank
    @Size(max = 5000)
    private String text;

    public Message(ChatMember chatMember, String text) {
        this.chatMember = chatMember;
        this.sentAt = LocalDateTime.now();
        this.text = text;
    }
}
