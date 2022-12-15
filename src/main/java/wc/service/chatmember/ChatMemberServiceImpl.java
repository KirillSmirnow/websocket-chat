package wc.service.chatmember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.exception.UserException;
import wc.model.ChatMember;
import wc.repository.ChatMemberRepository;
import wc.repository.ChatRepository;
import wc.service.Id;
import wc.service.user.CurrentUserService;
import wc.service.user.UserService;
import wc.service.user.UserView;
import wc.utility.query.partial.PartialQueryExecutor;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

@Service
@RequiredArgsConstructor
public class ChatMemberServiceImpl implements ChatMemberService {

    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final PartialQueryExecutor partialQueryExecutor;

    @Override
    @Transactional
    public Id<Long> joinChat(ChatJoining joining) {
        var user = currentUserService.get();
        var chat = chatRepository.findByInvitationCode(joining.getInvitationCode())
                .orElseThrow(() -> new UserException("Chat not found"));
        chatMemberRepository.save(new ChatMember(chat, user));
        return chat::getId;
    }

    @Override
    @Transactional
    public void leaveChat(long chatId) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public PartialQueryResult<UserView> getChatMembers(long chatId, PartialQueryParameters parameters) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var queryBuilder = new ChatMembersQueryBuilder(chat);
        return partialQueryExecutor.execute(
                queryBuilder,
                parameters,
                chatMember -> userService.getUser(chatMember.getUser().getId())
        );
    }
}
