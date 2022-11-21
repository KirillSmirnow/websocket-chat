package wc.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.model.Chat;
import wc.repository.ChatRepository;
import wc.service.Id;
import wc.service.chatmember.ChatJoining;
import wc.service.chatmember.ChatMemberService;
import wc.service.user.CurrentUserService;
import wc.utility.query.partial.PartialQueryExecutor;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMemberService chatMemberService;
    private final CurrentUserService currentUserService;
    private final PartialQueryExecutor partialQueryExecutor;

    @Override
    @Transactional
    public Id<Long> createChat(ChatCreation creation) {
        var chat = new Chat();
        chat.setInvitationCode(generateInvitationCode());
        chat.setName(creation.getName());
        var savedChat = chatRepository.save(chat);
        chatMemberService.joinChat(new ChatJoining(savedChat.getInvitationCode()));
        return savedChat::getId;
    }

    private String generateInvitationCode() {
        return randomUUID().toString().replace("-", "").substring(0, 16);
    }

    @Override
    @Transactional
    public void updateChat(long chatId, ChatUpdate update) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        chat.setName(update.getName());
    }

    @Override
    @Transactional
    public void updateInvitationCode(long chatId) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        chat.setInvitationCode(generateInvitationCode());
    }

    @Override
    @Transactional(readOnly = true)
    public ChatView getChat(long id) {
        var chat = chatRepository.findById(id).orElseThrow();
        return toView(chat);
    }

    private ChatView toView(Chat chat) {
        return ChatView.builder()
                .id(chat.getId())
                .invitationCode(chat.getInvitationCode())
                .name(chat.getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PartialQueryResult<ChatView> getCurrentUserChats(PartialQueryParameters parameters) {
        var user = currentUserService.get();
        var queryBuilder = new CurrentUserChatsQueryBuilder(user);
        return partialQueryExecutor.execute(queryBuilder, parameters, chatMember -> toView(chatMember.getChat()));
    }
}
