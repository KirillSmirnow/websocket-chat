package wc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wc.model.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByInvitationCode(String invitationCode);
}
