package study.movies.exception.crud;

import study.movies.exception.http.ForbiddenException;

public class PermissionDeniedException extends ForbiddenException {
    public PermissionDeniedException(String s) {
        super(s);
    }
}
