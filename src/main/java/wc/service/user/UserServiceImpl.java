package wc.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wc.model.User;
import wc.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(UserRegistration registration) {
        var user = new User();
        user.setNickname(registration.getNickname());
        user.setHashedPassword(passwordEncoder.encode(registration.getPassword()));
        user.setName(registration.getName());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateCurrentUser(UserUpdate update) {
        var user = currentUserService.get();
        user.setNickname(user.getNickname());
        user.setName(update.getName());
    }

    @Override
    @Transactional
    public void updateCurrentUserPassword(UserPasswordUpdate update) {
        var user = currentUserService.get();
        user.setHashedPassword(passwordEncoder.encode(update.getPassword()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserView getCurrentUser() {
        var user = currentUserService.get();
        return toView(user);
    }

    private UserView toView(User user) {
        return UserView.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserView getUser(long id) {
        var user = userRepository.findById(id).orElseThrow();
        return toView(user);
    }
}
