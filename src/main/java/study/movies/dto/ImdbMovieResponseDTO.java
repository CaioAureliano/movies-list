package study.movies.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(using = ImdbMovieResponseDeserialize.class)
public class ImdbMovieResponseDTO {

    private Integer id;

    private String title;

    private String overview;

    private String backdropPath;

}
