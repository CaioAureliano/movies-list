package study.movies.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserPasswordRequestDTO {

    @NotEmpty(message = "Required field")
    private String password;

}
