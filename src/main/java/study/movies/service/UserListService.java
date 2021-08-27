package study.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.movies.domain.MovieList;
import study.movies.domain.User;
import study.movies.domain.UserList;
import study.movies.dto.ImdbMovieResponseDTO;
import study.movies.dto.MessageResponse;
import study.movies.dto.MovieListRequestDTO;
import study.movies.dto.MoviesUserListResponseDTO;
import study.movies.dto.NameUserListRequestDTO;
import study.movies.dto.UserListRequestDTO;
import study.movies.dto.UserListResponseDTO;
import study.movies.exception.crud.EntityAlreadyExistsException;
import study.movies.exception.crud.NotHavePermissionToAddedException;
import study.movies.exception.crud.EntityNotFoundException;
import study.movies.exception.crud.MovieAlreadyExistsInList;
import study.movies.exception.http.BadRequestException;
import study.movies.mapper.UserListMapper;
import study.movies.repository.MovieListRepository;
import study.movies.repository.UserListRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserListService {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RequestApiService requestApiService;

    private static final UserListMapper USER_LIST_MAPPER = UserListMapper.INSTANCE;

    public Page<UserListResponseDTO> getAll(Pageable pageable) {
        User user = authenticationService.getUserInContext();
        return userListRepository.findAllByUser(user, pageable)
                .map(USER_LIST_MAPPER::toDTO);
    }

    @Transactional
    public UserListResponseDTO create(UserListRequestDTO request) {
        User user = authenticationService.getUserInContext();
        String listName = request.getName();

        if (userListRepository.existsByNameAndUser(listName, user)) {
            throw new EntityAlreadyExistsException(String.format("List with name %s already exists", listName));
        }

        UserList listToSave = USER_LIST_MAPPER.toModel(request);
        listToSave.setUser(user);
        UserList listSaved = userListRepository.save(listToSave);
        return USER_LIST_MAPPER.toDTO(listSaved);
    }

    @Transactional
    public MessageResponse addMovieToListByUser(Long listId, MovieListRequestDTO request) {
        UserList list = userListRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));

        isUserHavePermissionInList(list);

        if (movieListRepository.existsMovieById(request.getMovie())) {
            throw new MovieAlreadyExistsInList("Movie already exists in the list");
        }

        MovieList movieList = new MovieList();
        movieList.setImdbMovieId(request.getMovie());
        movieList.setUserList(list);
        movieListRepository.save(movieList);

        return MessageResponse.builder()
                .message("Movie added to list")
                .build();
    }

    public MoviesUserListResponseDTO findMoviesByList(Long id) {
        UserList userList = userListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));

        isUserHavePermissionInList(userList);

        List<ImdbMovieResponseDTO> imdbMovies = new ArrayList<>();
        List<Integer> imdbMoviesIds = movieListRepository.findImdbMovieIdByUserListId(userList.getId());
        for (Integer imdbMovieId : imdbMoviesIds) {
            imdbMovies.add(requestApiService.findMovieById(imdbMovieId));
        }

        return MoviesUserListResponseDTO.builder()
                .id(userList.getId())
                .name(userList.getName())
                .createdAt(userList.getCreatedAt())
                .movies(imdbMovies)
                .build();
    }

    @Transactional
    public MessageResponse updateNameById(NameUserListRequestDTO request, Long listId) {
        UserList list = userListRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));
        isUserHavePermissionInList(list);
        list.setName(request.getName());
        userListRepository.save(list);
        return MessageResponse.builder()
                .message("List name updated")
                .build();
    }

    @Transactional
    public MessageResponse removeById(Long listId) {
        UserList userList = userListRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found"));
        isUserHavePermissionInList(userList);
        userListRepository.delete(userList);
        return MessageResponse.builder()
                .message("List removed")
                .build();
    }

    private void isUserHavePermissionInList(UserList list) {
        User user = authenticationService.getUserInContext();
        if (!list.getUser().equals(user)) {
            throw new NotHavePermissionToAddedException("User not have permission to access this list");
        }
    }

}
