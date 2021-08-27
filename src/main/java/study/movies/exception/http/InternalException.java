package study.movies.exception.http;

public class InternalException extends RuntimeException {
    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String message) {
        super(message);
    }
}
