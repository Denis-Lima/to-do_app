package br.com.todoserver.todoapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.todoserver.todoapp.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    public List<UserEntity> findByUsernameContainsIgnoreCase(String username);

    public UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    public UserEntity searchByUsername(String username);

    public UserEntity findByUsernameAndPassword(String username, String password);

    public Optional<UserEntity> findByUsernameIgnoreCase(String username);

    public UserEntity findByEmailIgnoreCase(String email);

    public UserEntity findByEmailOrUsernameIgnoreCase(String email, String username);
}
