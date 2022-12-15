package wc.exception.converter;

import org.springframework.stereotype.Component;
import wc.exception.ConcreteExceptionToErrorConverter;
import wc.exception.Error;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.Comparator.comparing;

@Component
public class ConstraintViolationExceptionToErrorConverter implements ConcreteExceptionToErrorConverter<ConstraintViolationException> {

    @Override
    public Class<ConstraintViolationException> getConvertableExceptionType() {
        return ConstraintViolationException.class;
    }

    @Override
    public List<Error> convert(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(this::convert)
                .sorted(comparing(Error::getField))
                .toList();
    }

    private Error convert(ConstraintViolation<?> violation) {
        var nodeIterator = violation.getPropertyPath().iterator();
        var lastNode = nodeIterator.next();
        while (nodeIterator.hasNext()) lastNode = nodeIterator.next();
        return new Error(violation.getMessage(), lastNode.getName());
    }
}
