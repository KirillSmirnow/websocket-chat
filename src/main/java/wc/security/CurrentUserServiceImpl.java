package wc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wc.model.User;
import wc.repository.UserRepository;
import wc.service.user.CurrentUserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    @Override
    public boolean isAuthenticated() {
        return getAuthentication().isPresent();
    }

    private Optional<UserAuthentication> getAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UserAuthentication userAuthentication)) {
            return Optional.empty();
        }
        return Optional.of(userAuthentication);
    }

    @Override
    public User get() {
        var authentication = getAuthentication().orElseThrow();
        return userRepository.findById(authentication.getUserId()).orElseThrow();
    }
}
