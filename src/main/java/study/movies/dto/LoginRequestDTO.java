package study.movies.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDTO {

    @NotEmpty(message = "Required field")
    @Email
    private String email;

    @NotEmpty(message = "Required field")
    private String password;

}
