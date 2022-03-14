package br.com.todoserver.todoapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.models.Login;
import br.com.todoserver.todoapp.models.User;

@RestController
@RequestMapping("/login")
public class AuthController {

    @PostMapping("/")
    public Login login() {
        return new Login(1L, "denis@denis.com", "we", new User());
    }

    @GetMapping("/")
    public Login test() {
        return new Login(1L, "denis@denis.com", "we", new User());
    }
}