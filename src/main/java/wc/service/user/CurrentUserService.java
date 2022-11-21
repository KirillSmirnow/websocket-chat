package wc.service.user;

import wc.model.User;

public interface CurrentUserService {

    boolean isAuthenticated();

    User get();
}
