package study.movies.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.movies.domain.AuthenticatedUser;
import study.movies.domain.User;
import study.movies.dto.LoginRequestDTO;
import study.movies.dto.LoginResponseDTO;
import study.movies.exception.crud.EntityNotFoundException;
import study.movies.exception.crud.InvalidUserException;
import study.movies.repository.UserRepository;
import study.movies.security.TokenManager;

@Log4j2
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new AuthenticatedUser(
                user.getEmail(),
                user.getPassword()
        );
    }

    public LoginResponseDTO authenticateAndCreateToken(LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        UserDetails userDetails = loadUserByUsername(email);
        isValidPassword(loginRequest.getPassword(), userDetails.getPassword());
        authenticateUser(email, loginRequest.getPassword());
        String token = tokenManager.generate(userDetails);

        log.info(String.format("User logged: %s", email));

        return LoginResponseDTO.builder()
                .message("Login successful")
                .token(token)
                .build();
    }

    public User getUserInContext() {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authenticatedUser == null) {
            throw new InvalidUserException("User not found");
        }
        String userEmail = authenticatedUser.getUsername();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private void isValidPassword(String password, String hasPassword) {
        if (!passwordEncoder.matches(password, hasPassword)) {
            throw new InvalidUserException("Invalid email and/or password");
        }
    }

}
