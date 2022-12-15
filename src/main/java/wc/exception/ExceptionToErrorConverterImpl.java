package wc.exception;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class ExceptionToErrorConverterImpl implements ExceptionToErrorConverter {

    private final Map<Class<?>, ConcreteExceptionToErrorConverter<?>> converters;

    public ExceptionToErrorConverterImpl(Set<ConcreteExceptionToErrorConverter<?>> converters) {
        this.converters = converters.stream()
                .collect(toMap(ConcreteExceptionToErrorConverter::getConvertableExceptionType, identity()));
    }

    @Override
    public List<Error> convert(Throwable throwable) {
        var converter = converters.get(throwable.getClass());
        if (converter != null) {
            return convert(converter, throwable);
        }
        return List.of(Error.UNEXPECTED);
    }

    private <E extends Exception> List<Error> convert(ConcreteExceptionToErrorConverter<E> converter, Throwable throwable) {
        return converter.convert((E) throwable);
    }
}
