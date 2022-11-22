package wc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import wc.repository.SessionRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BearerTokenAuthenticationManager implements AuthenticationManager {

    private final SessionRepository sessionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = extractBearerToken(authentication);
        var session = sessionRepository.findById(token).orElseThrow(() -> new InvalidBearerTokenException("No session"));
        return new UserAuthentication(session.getUser().getId());
    }

    private UUID extractBearerToken(Authentication authentication) {
        if (!(authentication instanceof BearerTokenAuthenticationToken bearerTokenAuthentication)) {
            throw new InvalidBearerTokenException("Bearer token expected");
        }
        try {
            return UUID.fromString(bearerTokenAuthentication.getToken());
        } catch (Exception e) {
            throw new InvalidBearerTokenException("UUID token expected");
        }
    }
}
