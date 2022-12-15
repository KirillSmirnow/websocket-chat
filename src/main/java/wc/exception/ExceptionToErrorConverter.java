package wc.exception;

import java.util.List;

public interface ExceptionToErrorConverter {

    List<Error> convert(Throwable throwable);
}
