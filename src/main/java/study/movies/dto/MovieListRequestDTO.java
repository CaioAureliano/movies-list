package study.movies.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MovieListRequestDTO {

    @Min(1) @NotNull
    private Integer movie;

}
