package wc.exception.converter;

import org.springframework.stereotype.Component;
import wc.exception.ConcreteExceptionToErrorConverter;
import wc.exception.Error;
import wc.exception.UserException;

import java.util.List;

@Component
public class UserExceptionToErrorConverter implements ConcreteExceptionToErrorConverter<UserException> {

    @Override
    public Class<UserException> getConvertableExceptionType() {
        return UserException.class;
    }

    @Override
    public List<Error> convert(UserException e) {
        return List.of(new Error(e.getMessage(), null));
    }
}
