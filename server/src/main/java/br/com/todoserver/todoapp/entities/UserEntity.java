package br.com.todoserver.todoapp.entities;

import java.util.Collection;
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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "use_username" }, name = "user_use_username_ukey"),
        @UniqueConstraint(columnNames = { "use_email" }, name = "user_use_email_ukey")
})
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_cod")
    private Long id;
    
    @NotBlank(message = "User username can't be empty")
    @Column(name = "use_username")
    private String username;
    
    @NotBlank(message = "User email can't be empty")
    @Email(message = "Email must to be valid")
    @Column(name = "use_email")
    private String email;
    
    @NotBlank(message = "User password can't be empty")
    @Column(name = "use_password")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ProjectEntity> projects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
