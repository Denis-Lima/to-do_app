package br.com.todoserver.todoapp.dtos;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
