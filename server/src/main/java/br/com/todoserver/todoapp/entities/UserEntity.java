package br.com.todoserver.todoapp.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "use_username" }, name = "user_use_username_ukey"),
        @UniqueConstraint(columnNames = { "use_email" }, name = "user_use_email_ukey")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_cod")
    private Long id;
    
    @NotEmpty(message = "Username can't be null or empty")
    @Column(name = "use_username")
    private String username;
    
    @NotEmpty(message = "Email can't be null or empty")
    @Email(message = "Email must to be valid")
    @Column(name = "use_email")
    private String email;
    
    @NotEmpty(message = "Password can't be null or empty")
    @Column(name = "use_password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<TaskEntity> tasks;
}
