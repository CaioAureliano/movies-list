package study.movies.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserListRequestDTO {

    @NotEmpty(message = "Required field")
    private String name;

}
