package wc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(columnList = "user_id"),
})
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Session {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private User user;

    @NotNull
    private LocalDateTime openedAt;

    public Session(User user) {
        this.user = user;
        this.openedAt = LocalDateTime.now();
    }
}
