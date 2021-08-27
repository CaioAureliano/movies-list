package study.movies.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import study.movies.domain.Movie;
import study.movies.domain.MoviesResponseDeserializer;

import java.util.List;

@Data
@Builder
@JsonDeserialize(using = MoviesResponseDeserializer.class)
public class MoviesResponseDTO {

    private Integer page;

    private Integer totalPages;

    private Integer totalResults;

    private List<Movie> movies;

}
