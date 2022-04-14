package br.com.todoserver.todoapp.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.repositories.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User [%s] not found", username));
        }

        return new UserDetailsData(user);
    }

}
