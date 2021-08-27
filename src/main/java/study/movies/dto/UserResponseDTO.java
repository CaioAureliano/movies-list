package study.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
