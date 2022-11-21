package wc.service.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatView {
    private final long id;
    private final String invitationCode;
    private final String name;
}
