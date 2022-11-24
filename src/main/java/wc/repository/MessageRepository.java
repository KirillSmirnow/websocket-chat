package wc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wc.model.Message;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
