package wc.service.session;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Id<UUID> startSession(PasswordSignIn signIn) {
        var user = userRepository.findByNickname(signIn.getNickname()).orElseThrow();
        var passwordCorrect = passwordEncoder.matches(signIn.getPassword(), user.getHashedPassword());
        if (!passwordCorrect) {
            throw new RuntimeException("Incorrect password");
        }
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
