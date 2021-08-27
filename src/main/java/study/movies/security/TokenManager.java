package study.movies.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.movies.exception.http.InternalException;
import study.movies.security.exception.InvalidTokenException;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenManager {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generate(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validate(String token, UserDetails userDetails) {
        String subject = getSubjectFromToken(token);
        return (subject.equalsIgnoreCase(userDetails.getUsername()) && isExpired(token));
    }

    private boolean isExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return !expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException jwtException) {
            throw new InvalidTokenException("Invalid token", jwtException);
        } catch (Exception e) {
            throw new InternalException("Could not identify token", e);
        }
    }

    private String createToken(String s) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setSubject(s)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(KEY)
                .compact();
    }

}
