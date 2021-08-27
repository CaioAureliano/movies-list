package study.movies.security.exception;

import study.movies.exception.http.BadRequestException;

public class InvalidTokenException extends BadRequestException {
    public InvalidTokenException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
