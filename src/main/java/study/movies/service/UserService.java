package study.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.movies.controller.UserUpdateRequestDTO;
import study.movies.domain.User;
import study.movies.dto.MessageResponse;
import study.movies.dto.UserPasswordRequestDTO;
import study.movies.dto.UserRequestDTO;
import study.movies.dto.UserResponseDTO;
import study.movies.exception.crud.EntityExistsException;
import study.movies.exception.crud.PermissionDeniedException;
import study.movies.mapper.UserMapper;
import study.movies.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    private static final UserMapper USER_MAPPER = UserMapper.INSTANCCE;

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        if (existsWithEmail(userRequestDTO.getEmail())) {
            throw new EntityExistsException("Exists user with that email");
        }

        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        User userToSave = USER_MAPPER.toModel(userRequestDTO);
        userToSave.setPassword(encodedPassword);
        User userSaved = userRepository.save(userToSave);
        return USER_MAPPER.toDTO(userSaved);
    }

    @Transactional
    public UserResponseDTO updateById(UserUpdateRequestDTO request, Long id) {
        User userInContext = authenticationService.getUserInContext();
        userHavePermissionToUpdateData(userInContext.getId(), id);
        User userToSave = USER_MAPPER.toModel(request);
        userToSave.setId(userInContext.getId());
        userToSave.setPassword(userInContext.getPassword());
        User userUpdated = userRepository.save(userToSave);
        return USER_MAPPER.toDTO(userUpdated);
    }

    public UserResponseDTO getDetailsByContext() {
        User user = authenticationService.getUserInContext();
        return USER_MAPPER.toDTO(user);
    }

    @Transactional
    public MessageResponse updatePasswordByIdAndContext(UserPasswordRequestDTO request, Long id) {
        User userInContext = authenticationService.getUserInContext();
        userHavePermissionToUpdateData(userInContext.getId(), id);
        userInContext.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userInContext);
        return MessageResponse.builder()
                .message("Password updated")
                .build();
    }

    private boolean existsWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void userHavePermissionToUpdateData(Long userInContextId, Long userRequestId) {
        if (!userInContextId.equals(userRequestId)) {
            throw new PermissionDeniedException("User not have permission to update data");
        }
    }

}
