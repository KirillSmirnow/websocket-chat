package wc.exception;

import java.util.List;

public interface ConcreteExceptionToErrorConverter<E extends Exception> {

    Class<E> getConvertableExceptionType();

    List<Error> convert(E e);
}
