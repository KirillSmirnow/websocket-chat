package wc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wc.model.Session;
import wc.model.User;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Page<Session> findByUser(User user, Pageable pageable);
}
