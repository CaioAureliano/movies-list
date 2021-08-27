package study.movies.exception.crud;

import study.movies.exception.http.BadRequestException;

public class MovieAlreadyExistsInList extends BadRequestException {
    public MovieAlreadyExistsInList(String s) {
        super(s);
    }
}
