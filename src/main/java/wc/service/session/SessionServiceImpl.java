package wc.service.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.model.Session;
import wc.repository.SessionRepository;
import wc.repository.UserRepository;
import wc.service.Id;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public Id<UUID> startSession(PasswordSignIn signIn) {
        var user = userRepository.findByNickname(signIn.getNickname()).orElseThrow();
        // TODO: check password
        var session = sessionRepository.save(new Session(user));
        return session::getId;
    }

    @Override
    @Transactional(readOnly = true)
    public SessionView getSession(UUID id) {
        var session = sessionRepository.findById(id).orElseThrow();
        return SessionView.builder()
                .id(session.getId())
                .openedAt(session.getOpenedAt())
                .build();
    }
}
