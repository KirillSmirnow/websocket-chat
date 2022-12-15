package wc.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wc.exception.Error;
import wc.exception.ExceptionToErrorConverter;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final ExceptionToErrorConverter exceptionToErrorConverter;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Error> handleAnyException(Throwable throwable) {
        return exceptionToErrorConverter.convert(throwable);
    }
}
