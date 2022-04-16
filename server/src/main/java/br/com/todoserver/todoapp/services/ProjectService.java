package br.com.todoserver.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.entities.ProjectEntity;
import br.com.todoserver.todoapp.mappers.ProjectionMapper;
import br.com.todoserver.todoapp.projections.ProjectProjection;
import br.com.todoserver.todoapp.repositories.ProjectRepository;
import br.com.todoserver.todoapp.repositories.UserRepository;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    public ProjectProjection.WithoutUser createProject(String name, Long userId) {
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        project.setUser(userRepository.findById(userId).get());
        projectRepository.save(project);
        return ProjectionMapper.convertObject(ProjectProjection.WithoutUser.class, project);
    }
}
