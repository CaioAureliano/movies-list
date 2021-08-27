package study.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.movies.dto.LoginRequestDTO;
import study.movies.dto.LoginResponseDTO;
import study.movies.dto.MessageResponse;
import study.movies.dto.UserPasswordRequestDTO;
import study.movies.dto.UserRequestDTO;
import study.movies.dto.UserResponseDTO;
import study.movies.service.AuthenticationService;
import study.movies.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticateAndCreateToken(loginRequestDTO));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.create(userRequestDTO));
    }

    @GetMapping("/details")
    public ResponseEntity<UserResponseDTO> getUSerDetailsByToken() {
        return ResponseEntity.ok(userService.getDetailsByContext());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateByIdAndToken(@RequestBody @Valid UserUpdateRequestDTO request, @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateById(request, id));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody @Valid UserPasswordRequestDTO request, @PathVariable Long id) {
        return ResponseEntity.ok(userService.updatePasswordByIdAndContext(request, id));
    }

}
