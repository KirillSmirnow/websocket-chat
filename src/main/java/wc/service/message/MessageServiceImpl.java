package wc.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.model.Message;
import wc.repository.ChatMemberRepository;
import wc.repository.ChatRepository;
import wc.repository.MessageRepository;
import wc.service.user.CurrentUserService;
import wc.service.user.UserService;
import wc.utility.query.partial.PartialQueryExecutor;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final PartialQueryExecutor partialQueryExecutor;
    private final Set<MessageSentListener> messageSentListeners;

    @Override
    @Transactional
    public void sendMessage(long chatId, MessageSending sending) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var user = currentUserService.get();
        var chatMember = chatMemberRepository.findByChatAndUser(chat, user).orElseThrow();
        var message = messageRepository.save(new Message(chatMember, sending.getText()));
        messageSentListeners.forEach(listener -> listener.onMessageSent(message));
    }

    @Override
    @Transactional(readOnly = true)
    public PartialQueryResult<MessageView> getChatMessages(long chatId, PartialQueryParameters parameters) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var queryBuilder = new ChatMessagesQueryBuilder(chat);
        return partialQueryExecutor.execute(queryBuilder, parameters, this::toView);
    }

    private MessageView toView(Message message) {
        return MessageView.builder()
                .id(message.getId())
                .sender(userService.getUser(message.getChatMember().getUser().getId()))
                .sentAt(message.getSentAt())
                .text(message.getText())
                .build();
    }
}
