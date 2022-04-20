package br.com.todoserver.todoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoserver.todoapp.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    public List<TaskEntity> findByProject_Id(Long projectId);
}
