package wc.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.model.ChatMember;
import wc.model.Message;
import wc.repository.ChatMemberRepository;
import wc.repository.ChatRepository;
import wc.repository.MessageRepository;
import wc.service.Id;
import wc.service.user.CurrentUserService;
import wc.service.user.UserService;
import wc.utility.query.partial.PartialQueryExecutor;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final PartialQueryExecutor partialQueryExecutor;
    private final Set<NewMessageNotifier> newMessageNotifiers;

    @Override
    @Transactional
    public Id<UUID> sendMessage(long chatId, MessageSending sending) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var user = currentUserService.get();
        var chatMember = chatMemberRepository.findByChatAndUser(chat, user).orElseThrow();
        var message = messageRepository.save(new Message(chatMember, sending.getText()));
        notifyAboutNewMessage(message);
        return message::getId;
    }

    private void notifyAboutNewMessage(Message message) {
        var messageView = toView(message);
        var chat = message.getChatMember().getChat();
        var recipients = chatMemberRepository.findByChat(chat).stream()
                .map(ChatMember::getUser)
                .collect(toSet());
        for (var recipient : recipients) {
            newMessageNotifiers.forEach(notifier -> notifier.notifyAboutNewMessage(recipient.getId(), messageView));
        }
    }

    private MessageView toView(Message message) {
        return MessageView.builder()
                .id(message.getId())
                .sender(userService.getUser(message.getChatMember().getUser().getId()))
                .sentAt(message.getSentAt())
                .text(message.getText())
                .build();
    }

    @Override
    public MessageView getMessage(UUID id) {
        var message = messageRepository.findById(id).orElseThrow();
        return toView(message);
    }

    @Override
    @Transactional(readOnly = true)
    public PartialQueryResult<MessageView> getChatMessages(long chatId, PartialQueryParameters parameters) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var queryBuilder = new ChatMessagesQueryBuilder(chat);
        return partialQueryExecutor.execute(queryBuilder, parameters, this::toView);
    }
}
