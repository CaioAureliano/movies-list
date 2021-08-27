package study.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.movies.dto.MoviesResponseDTO;
import study.movies.service.MovieListService;

@RestController
@RequestMapping("/movies")
public class MovieListController {

    @Autowired
    private MovieListService movieListService;

    @GetMapping
    public ResponseEntity<MoviesResponseDTO> getAll(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "en-US", required = false) String language) {
        return ResponseEntity.ok(movieListService.getAllMovies(page, language));
    }

    @GetMapping("/search")
    public ResponseEntity<MoviesResponseDTO> findByTitle(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "en-US", required = false) String language, @RequestParam String title) {
        return ResponseEntity.ok(movieListService.findAllByTitle(title, page, language));
    }

}
