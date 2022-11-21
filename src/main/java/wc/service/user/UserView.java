package wc.service.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {
    private final long id;
    private final String nickname;
    private final String name;
}
