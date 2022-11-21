package wc.service.session;

import lombok.Data;

@Data
public class PasswordSignIn {
    private final String nickname;
    private final String password;
}
