package wc.service.user;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {

    void registerUser(@Valid UserRegistration registration);

    void updateCurrentUser(@Valid UserUpdate update);

    void updateCurrentUserPassword(@Valid UserPasswordUpdate update);

    UserView getCurrentUser();

    UserView getUser(long id);
}
