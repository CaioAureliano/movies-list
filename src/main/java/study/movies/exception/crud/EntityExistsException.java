package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class EntityExistsException extends BadRequestException {
    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
