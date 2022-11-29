package wc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import wc.model.Session;
import wc.repository.SessionRepository;
import wc.service.message.MessageView;
import wc.service.message.NewMessageNotifier;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MessagesWebSocketHandler extends TextWebSocketHandler implements NewMessageNotifier {

    private final Map<Long, Set<WebSocketSession>> sessionsByUserId = new ConcurrentHashMap<>();

    private final SessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        var token = UUID.fromString(message.getPayload());
        var user = sessionRepository.findById(token).map(Session::getUser).orElseThrow();
        sessionsByUserId.computeIfAbsent(user.getId(), $ -> ConcurrentHashMap.newKeySet()).add(session);
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
