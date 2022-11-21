package wc.service.session;

import org.springframework.validation.annotation.Validated;
import wc.service.Id;

import javax.validation.Valid;
import java.util.UUID;

@Validated
public interface SessionService {

    Id<UUID> startSession(@Valid PasswordSignIn signIn);

    SessionView getSession(UUID id);
}
