package wc.service.message;

import lombok.Builder;
import lombok.Data;
import wc.service.chat.ChatView;
import wc.service.user.UserView;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MessageView {
    private final UUID id;
    private final ChatView chat;
    private final UserView sender;
    private final LocalDateTime sentAt;
    private final String text;
}
