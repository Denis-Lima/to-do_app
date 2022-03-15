package br.com.todoserver.todoapp.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}, name = "uc_username"),
        @UniqueConstraint(columnNames = {"email"}, name = "uc_email")
    })
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty(message = "Username can't be null or empty")
    private String username;
    
    @NotEmpty(message = "Email can't be null or empty")
    @Email(message = "Email must to be valid")
    private String email;
    
    @NotEmpty(message = "Password can't be null or empty")
    private String password;
}
