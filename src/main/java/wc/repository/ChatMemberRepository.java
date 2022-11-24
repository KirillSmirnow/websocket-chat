package wc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wc.model.Chat;
import wc.model.ChatMember;
import wc.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChatMemberRepository extends JpaRepository<ChatMember, UUID> {

    Optional<ChatMember> findByChatAndUser(Chat chat, User user);

    Set<ChatMember> findByChat(Chat chat);
}
