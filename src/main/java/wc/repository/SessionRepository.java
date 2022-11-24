package wc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wc.model.Session;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
