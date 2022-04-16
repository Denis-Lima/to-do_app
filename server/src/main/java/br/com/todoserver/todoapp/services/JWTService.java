package br.com.todoserver.todoapp.services;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secretkey}")
    private String secret;

    private String name = "jwtoken";

    public String getName() {
        return this.name;
    }

    public String generateToken(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(this.expiration));

        return Jwts.builder().setIssuer("todoapp").setSubject(user.getId().toString()).setIssuedAt(now)
                .setExpiration(exp).signWith(SignatureAlgorithm.HS256, this.secret).compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdFromToken(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.valueOf(body.getSubject());
    }

    public Integer getExpFromToken(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return (Integer) (int) body.getExpiration().getTime();
    }

    public boolean isSamePerson(Long id, Cookie[] cookies) {
        String token = this.getTokenFromCookies(cookies);
        if (token != null)
            return this.getIdFromToken(token).equals(id);
        return false;
    }

    public String getTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(this.name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
