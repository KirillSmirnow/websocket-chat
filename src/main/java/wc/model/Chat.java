package wc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(indexes = {
        @Index(columnList = "invitationCode", unique = true),
        @Index(columnList = "name"),
})
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Chat {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotBlank
    @Size(max = 16)
    @Setter
    private String invitationCode;

    @NotNull
    @NotBlank
    @Size(max = 32)
    @Setter
    private String name;
}
