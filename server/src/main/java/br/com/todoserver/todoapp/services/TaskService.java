package br.com.todoserver.todoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.todoserver.todoapp.dtos.TaskDTO;
import br.com.todoserver.todoapp.entities.ProjectEntity;
import br.com.todoserver.todoapp.entities.TaskEntity;
import br.com.todoserver.todoapp.exceptions.ResourceNotFoundException;
import br.com.todoserver.todoapp.exceptions.UnauthorizedPermissionException;
import br.com.todoserver.todoapp.mappers.UpdateMapper;
import br.com.todoserver.todoapp.repositories.ProjectRepository;
import br.com.todoserver.todoapp.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UpdateMapper updateMapper;

    private boolean isUserProject(ProjectEntity project) {
        Long projectId = project.getId();
        Long userId = project.getUser().getId();
        return projectRepository.findByIdAndUser_Id(projectId, userId).isPresent();
    }

    public TaskEntity createTask(TaskEntity task) {
        if (!isUserProject(task.getProject()))
            throw new ResourceNotFoundException("Project not found for the requested user");
        task.setFinished(false);
        return taskRepository.save(task);
    }

    public TaskEntity updateTask(Long taskId, TaskDTO newTask, Long userId) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty())
            throw new ResourceNotFoundException(String.format("Task [%d] not found", taskId));

        TaskEntity task = optionalTask.get();
        task.getProject().getUser().setId(userId);
        if (!isUserProject(task.getProject()))
            throw new ResourceNotFoundException("Project not found for the requested user");
        updateMapper.updateTaskFromDto(newTask, task);
        return taskRepository.save(task);
    }

    public TaskEntity getTask(Long taskId, Long userId) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty())
            throw new ResourceNotFoundException(String.format("Task [%d] not found", taskId));
        if (optionalTask.get().getProject().getUser().getId() != userId)
            throw new UnauthorizedPermissionException("This task isn't yours");
        return optionalTask.get();
    }

    public List<TaskEntity> getTasks(Long projectId, Long userId) {
        List<TaskEntity> taskList = taskRepository.findByProject_Id(projectId);
        if (taskList.size() > 0 && taskList.get(0).getProject().getUser().getId() != userId)
            throw new UnauthorizedPermissionException("You can't access tasks from other users project");

        return taskList;
    }

    public void deleteTask(Long taskId, Long userId) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty())
            throw new ResourceNotFoundException(String.format("Task [%d] not found", taskId));
        
        TaskEntity task = optionalTask.get();
        if (task.getProject().getUser().getId() != userId)
            throw new UnauthorizedPermissionException("You can't delete task of other users");

        taskRepository.delete(task);
    }
}
