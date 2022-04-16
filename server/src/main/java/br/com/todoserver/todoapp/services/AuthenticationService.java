package br.com.todoserver.todoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(username, username);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
