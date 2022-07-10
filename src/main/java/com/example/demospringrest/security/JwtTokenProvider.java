package com.example.demospringrest.security;

import com.example.demospringrest.exception.ApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-wxpiration-milliseconds}")
    private int jwtExpiration;

    public String generationToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDt = new Date();
        Date expireDt = new Date(currentDt.getTime() + jwtExpiration);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDt)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid");
        } catch (UnsupportedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid");
        } catch (MalformedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid");
        } catch (SignatureException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid");
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid");
        }
    }


}
