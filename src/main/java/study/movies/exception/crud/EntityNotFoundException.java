package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
