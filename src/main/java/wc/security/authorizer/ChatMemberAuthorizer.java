package wc.security.authorizer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import wc.repository.ChatMemberRepository;
import wc.repository.ChatRepository;
import wc.service.user.CurrentUserService;

@Component
@RequiredArgsConstructor
public class ChatMemberAuthorizer {

    private final CurrentUserService currentUserService;
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;

    @PreAuthorize("isAuthenticated()")
    public boolean isCurrentUserMemberOfChat(long chatId) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        var user = currentUserService.get();
        return chatMemberRepository.findByChatAndUser(chat, user).isPresent();
    }
}
