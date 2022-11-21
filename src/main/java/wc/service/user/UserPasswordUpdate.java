package wc.service.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserPasswordUpdate {

    @NotNull
    @NotBlank
    @Size(max = 32)
    private final String password;
}
