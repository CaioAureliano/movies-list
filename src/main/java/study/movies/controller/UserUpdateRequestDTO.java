package study.movies.controller;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateRequestDTO {

    private Integer id;

    @NotEmpty(message = "Required field")
    private String firstname;

    @NotEmpty(message = "Required field")
    private String lastname;

    @Email(message = "Invalid email")
    private String email;

}
