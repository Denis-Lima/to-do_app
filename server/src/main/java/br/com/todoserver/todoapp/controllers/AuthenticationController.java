package br.com.todoserver.todoapp.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.dtos.LoginDTO;
import br.com.todoserver.todoapp.services.JWTService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @ResponseBody
    @PostMapping
    public void auth(@RequestBody @Validated LoginDTO login, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = jwtService.generateToken(authentication);
        Cookie cookie = new Cookie(jwtService.getName(), token);
        cookie.setMaxAge(jwtService.getExpFromToken(token));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(200);
    }
}
