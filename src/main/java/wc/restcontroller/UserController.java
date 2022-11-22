package wc.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wc.security.annotation.AuthenticationRequired;
import wc.service.session.PasswordSignIn;
import wc.service.session.SessionService;
import wc.service.session.SessionView;
import wc.service.user.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/users")
    public SessionView registerUser(@RequestBody UserRegistration registration) {
        userService.registerUser(registration);
        var signIn = new PasswordSignIn(registration.getNickname(), registration.getPassword());
        var id = sessionService.startSession(signIn);
        return sessionService.getSession(id.value());
    }

    @PutMapping("/users/me")
    @AuthenticationRequired
    public UserView updateCurrentUser(@RequestBody UserUpdate update) {
        userService.updateCurrentUser(update);
        return userService.getCurrentUser();
    }

    @PutMapping("/users/me/password")
    @AuthenticationRequired
    public UserView updateCurrentUserPassword(@RequestBody UserPasswordUpdate update) {
        userService.updateCurrentUserPassword(update);
        return userService.getCurrentUser();
    }

    @GetMapping("/users/me")
    @AuthenticationRequired
    public UserView getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/sessions")
    public SessionView startSession(@RequestBody PasswordSignIn signIn) {
        var id = sessionService.startSession(signIn);
        return sessionService.getSession(id.value());
    }
}
