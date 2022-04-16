package br.com.todoserver.todoapp.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.dtos.UserDto;
import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.exceptions.UnauthorizedPermissionException;
import br.com.todoserver.todoapp.responses.SuccessResponse;
import br.com.todoserver.todoapp.services.JWTService;
import br.com.todoserver.todoapp.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody UserEntity user) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(userService.createUser(user)),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@Valid @RequestBody UserDto user, @PathVariable("id") Long id,
            HttpServletRequest request) {
        if (!jwtService.isSamePerson(id, request.getCookies()))
            throw new UnauthorizedPermissionException("The authenticated user and the id isn't the same");
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(userService.updateUser(
                id, user)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> get(@PathVariable("id") Long id, HttpServletRequest request) {
        if (!jwtService.isSamePerson(id, request.getCookies()))
            throw new UnauthorizedPermissionException("The authenticated user and the id isn't the same");
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(userService.getUser(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        if (!jwtService.isSamePerson(id, request.getCookies()))
            throw new UnauthorizedPermissionException("The authenticated user and the id isn't the same");
        userService.deleteUser(id);
        response.setStatus(200);
        response.addCookie(new Cookie(jwtService.getName(), null));
    }

}
