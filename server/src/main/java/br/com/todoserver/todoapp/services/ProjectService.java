package br.com.todoserver.todoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.entities.ProjectEntity;
import br.com.todoserver.todoapp.exceptions.ResourceNotFoundException;
import br.com.todoserver.todoapp.exceptions.UnauthorizedPermissionException;
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

    public ProjectProjection.WithoutUser getProject(Long projectId, Long userId) {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty())
            throw new ResourceNotFoundException(String.format("Project [%d] not found", projectId));
        if (optionalProject.get().getUser().getId() != userId)
            throw new UnauthorizedPermissionException(String.format("Project [%d] isn't yours", projectId));
        return ProjectionMapper.convertObject(ProjectProjection.WithoutUser.class, optionalProject.get());
    }

    public List<ProjectProjection.WithoutUser> getProjects(Long userId) {
        List<ProjectEntity> projects = projectRepository.findByUser_Id(userId);
        return ProjectionMapper.convertObjectList(ProjectProjection.WithoutUser.class, projects);
    }

    public ProjectProjection.WithoutUser updateProjectName(Long projectId, Long userId, String newName) {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty())
            throw new ResourceNotFoundException(String.format("Project [%d] not found", projectId));
        if (optionalProject.get().getUser().getId() != userId)
            throw new UnauthorizedPermissionException(String.format("Project [%d] isn't yours", projectId));
        
        ProjectEntity project = optionalProject.get();
        project.setName(newName);
        projectRepository.save(project);
        return ProjectionMapper.convertObject(ProjectProjection.WithoutUser.class, project);
    }

    public ProjectEntity removeProject(Long projectId, Long userId) {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty())
            throw new ResourceNotFoundException(String.format("Project [%d] not found", projectId));
        if (optionalProject.get().getUser().getId() != userId)
            throw new UnauthorizedPermissionException(String.format("Project [%d] isn't yours", projectId));
        
        ProjectEntity project = optionalProject.get();
        projectRepository.delete(project);
        return project;
    }
}
