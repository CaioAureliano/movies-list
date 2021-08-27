package study.movies.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserRequestDTO {

    @NotEmpty(message = "Required field")
    private String firstname;

    @NotEmpty(message = "Required field")
    private String lastname;

    @NotEmpty(message = "Required field") @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Required field")
    private String password;

}
