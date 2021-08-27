package study.movies.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import study.movies.domain.UserList;
import study.movies.dto.UserListRequestDTO;
import study.movies.dto.UserListResponseDTO;

@Mapper
public interface UserListMapper {

    UserListMapper INSTANCE = Mappers.getMapper(UserListMapper.class);

    UserList toModel(UserListRequestDTO dto);

    UserListResponseDTO toDTO(UserList model);

}
