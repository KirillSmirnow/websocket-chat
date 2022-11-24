package wc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import wc.security.UserAuthentication;
import wc.service.message.MessageView;
import wc.service.message.NewMessageNotifier;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MessagesWebSocketHandler extends TextWebSocketHandler implements NewMessageNotifier {

    private final Map<Long, Set<WebSocketSession>> sessionsByUserId = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (session.getPrincipal() instanceof UserAuthentication userAuthentication) {
            var userId = userAuthentication.getUserId();
            sessionsByUserId.computeIfAbsent(userId, $ -> ConcurrentHashMap.newKeySet()).add(session);
        }
    }

    @Override
    @SneakyThrows
    public void notifyAboutNewMessage(long recipientId, MessageView message) {
        var recipientSessions = sessionsByUserId.get(recipientId);
        if (recipientSessions == null || recipientSessions.isEmpty()) {
            return;
        }
        var payload = objectMapper.writeValueAsString(message);
        for (var recipientSession : recipientSessions) {
            if (recipientSession.isOpen()) {
                recipientSession.sendMessage(new TextMessage(payload));
            }
        }
    }
}
