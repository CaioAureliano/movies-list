package study.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.movies.dto.MessageResponse;
import study.movies.dto.MovieListRequestDTO;
import study.movies.dto.MoviesUserListResponseDTO;
import study.movies.dto.NameUserListRequestDTO;
import study.movies.dto.UserListRequestDTO;
import study.movies.dto.UserListResponseDTO;
import study.movies.service.UserListService;

import javax.validation.Valid;

@RestController
@RequestMapping("/lists")
public class UserListController {

    @Autowired
    private UserListService userListService;

    @GetMapping
    public ResponseEntity<Page<UserListResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userListService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<UserListResponseDTO> createUserList(@RequestBody @Valid UserListRequestDTO request) {
        return ResponseEntity.ok(userListService.create(request));
    }

    @PostMapping("/{list_id}/movie")
    public ResponseEntity<MessageResponse> addMovieToList(@PathVariable("list_id") Long listId, @RequestBody @Valid MovieListRequestDTO request) {
        return ResponseEntity.ok(userListService.addMovieToListByUser(listId, request));
    }

    @GetMapping("/{list_id}")
    public ResponseEntity<MoviesUserListResponseDTO> findMoviesByList(@PathVariable("list_id") Long id) {
        return ResponseEntity.ok(userListService.findMoviesByList(id));
    }

    @PatchMapping("/{list_id}")
    public ResponseEntity<MessageResponse> updateListName(@RequestBody @Valid NameUserListRequestDTO request, @PathVariable("list_id") Long listId) {
        return ResponseEntity.ok(userListService.updateNameById(request, listId));
    }

    @DeleteMapping("/{list_id}")
    public ResponseEntity<MessageResponse> removeMovieFromList(@PathVariable("list_id") Long listId) {
        return ResponseEntity.ok(userListService.removeById(listId));
    }

}
