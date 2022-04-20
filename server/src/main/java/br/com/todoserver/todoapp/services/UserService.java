package br.com.todoserver.todoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.dtos.UserDTO;
import br.com.todoserver.todoapp.entities.UserEntity;
import br.com.todoserver.todoapp.exceptions.ResourceAlreadyExistsException;
import br.com.todoserver.todoapp.exceptions.ResourceNotFoundException;
import br.com.todoserver.todoapp.mappers.ProjectionMapper;
import br.com.todoserver.todoapp.mappers.UpdateMapper;
import br.com.todoserver.todoapp.projections.UserProjection;
import br.com.todoserver.todoapp.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UpdateMapper updateMapper;

    public UserProjection.NoPassword createUser(UserEntity user) {
        if (userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(),
                user.getUsername()).isPresent())
            throw new ResourceAlreadyExistsException("This email or username already exists");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ProjectionMapper.convertObject(UserProjection.NoPassword.class, user);
    }

    public UserProjection.NoPassword updateUser(Long userId, UserDTO newUser) {
        Optional<UserEntity> optUser = userRepository.findById(userId);
        if (optUser.isEmpty())
            throw new ResourceNotFoundException(String.format("User [%d] not found", userId));

        UserEntity user = optUser.get();
        if (newUser.getPassword() != null)
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        updateMapper.updateUserFromDto(newUser, user);
        userRepository.save(user);
        return ProjectionMapper.convertObject(UserProjection.NoPassword.class, user);
    }

    public UserProjection.WithProjects getUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new ResourceNotFoundException(String.format("User [%d] not found", id));

        return ProjectionMapper.convertObject(UserProjection.WithProjects.class, optionalUser.get());
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new ResourceNotFoundException(String.format("User [%d] not found", id));

        userRepository.delete(optionalUser.get());
    }
}
