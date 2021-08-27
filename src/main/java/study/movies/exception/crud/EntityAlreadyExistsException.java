package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class EntityAlreadyExistsException extends BadRequestException {
    public EntityAlreadyExistsException(String s) {
        super(s);
    }
}
