package study.movies.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import study.movies.dto.ImdbMovieResponseDTO;
import study.movies.dto.MoviesResponseDTO;

@Service
public class RequestApiService {

    @Value("${app.api.url}")
    private String apiUrl;

    @Value("${app.api.token}")
    private String apiToken;

    public MoviesResponseDTO getMovies(Integer page, String language) {
        String path = String.format("%s%s?page=%s&language=%s", apiUrl, "/movie/popular", page, language);
        return request(path, MoviesResponseDTO.class);
    }

    public MoviesResponseDTO findMoviesByTitle(String title, Integer page, String language) {
        String path = String.format("%s%s?page=%s&language=%s&query=%s", apiUrl, "/search/movie", page, language, title);
        return request(path, MoviesResponseDTO.class);
    }

    public ImdbMovieResponseDTO findMovieById(Integer id) {
        String path = String.format("%s%s%s", apiUrl, "/movie/", id);
        return request(path, ImdbMovieResponseDTO.class);
    }

    private <T> T request(String path, Class<T> obj) {
        return WebClient.create(path)
                .get()
                .header("Authorization", String.format("Bearer %s", apiToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(obj)
                .block();
    }

}
