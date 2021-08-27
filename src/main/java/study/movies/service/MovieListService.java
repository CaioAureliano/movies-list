package study.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.movies.dto.MoviesResponseDTO;
import study.movies.exception.http.BadRequestException;

@Service
public class MovieListService {

    @Autowired
    private RequestApiService requestApiService;

    public MoviesResponseDTO getAllMovies(Integer page, String language) {
        return requestApiService.getMovies(page, language);
    }

    public MoviesResponseDTO findAllByTitle(String title, Integer page, String language) throws BadRequestException {
        if (title == null || title.isEmpty()) {
            throw new BadRequestException("Title is required");
        }
        return requestApiService.findMoviesByTitle(title, page, language);
    }
}
