package study.movies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import study.movies.service.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String email = null;
            String token = null;

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                email = tokenManager.getSubjectFromToken(token);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                addUserInContext(request, email, token);
            }

            chain.doFilter(request, response);

        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    private void addUserInContext(HttpServletRequest request, String email, String token) {
        UserDetails userDetails = authenticationService.loadUserByUsername(email);
        if (tokenManager.validate(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);;
        }
    }
}
