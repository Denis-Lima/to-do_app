package br.com.todoserver.todoapp.controllers;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.dtos.UserDto;
import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.responses.SuccessResponse;
import br.com.todoserver.todoapp.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody UserEntity user) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(userService.createUser(user)),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@Valid @RequestBody UserDto user, @PathParam("id") Long id) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(userService.updateUser(
                id, user)), HttpStatus.OK);
    }

}
