package wc.service.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegistration {

    @NotNull
    @NotBlank
    @Size(max = 16)
    private final String nickname;

    @NotNull
    @NotBlank
    @Size(max = 32)
    private final String password;

    @NotNull
    @NotBlank
    @Size(max = 32)
    private final String name;
}
