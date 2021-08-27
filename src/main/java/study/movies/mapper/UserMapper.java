package study.movies.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import study.movies.controller.UserUpdateRequestDTO;
import study.movies.domain.User;
import study.movies.dto.UserRequestDTO;
import study.movies.dto.UserResponseDTO;

@Mapper
public interface UserMapper {

    UserMapper INSTANCCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserRequestDTO dto);

    User toModel(UserUpdateRequestDTO dto);

    UserResponseDTO toDTO(User model);

}
