package wc.exception;

import lombok.Data;

@Data
public class Error {

    public static final Error UNEXPECTED = new Error("Something went wrong ツ", null);

    private final String message;
    private final String field;
}
