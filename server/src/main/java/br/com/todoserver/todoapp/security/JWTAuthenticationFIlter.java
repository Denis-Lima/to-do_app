package br.com.todoserver.todoapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.repositories.UserRepository;
import br.com.todoserver.todoapp.services.JWTService;

public class JWTAuthenticationFIlter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JWTAuthenticationFIlter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtService.getTokenFromCookies(request.getCookies());
        if (token != null && jwtService.isValid(token)) {
            this.authenticate(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(String token) {
        Long id = jwtService.getIdFromToken(token);
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null,
                    new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
