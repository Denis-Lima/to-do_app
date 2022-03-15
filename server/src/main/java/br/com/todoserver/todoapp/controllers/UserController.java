package br.com.todoserver.todoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.exceptions.ResourceAlreadyExistsException;
import br.com.todoserver.todoapp.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public UserEntity create(@Valid @RequestBody UserEntity user) throws ResourceAlreadyExistsException, Exception {
        UserEntity userExists = userRepository.findByEmailOrUsernameIgnoreCase(user.getEmail(), user.getUsername());
        if (userExists != null) {
            throw new ResourceAlreadyExistsException("User already exists");
        }

        try {
            UserEntity newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            throw new Exception("Erro", e);
        }
    }
    
}
