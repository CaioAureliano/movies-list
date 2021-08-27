package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class NotHavePermissionToAddedException extends BadRequestException {
    public NotHavePermissionToAddedException(String s) {
        super(s);
    }
}
