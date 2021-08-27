package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class InvalidUserException extends BadRequestException {
    public InvalidUserException(String s) {
        super(s);
    }
}
