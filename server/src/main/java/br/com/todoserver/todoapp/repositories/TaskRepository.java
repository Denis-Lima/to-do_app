package br.com.todoserver.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoserver.todoapp.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    
}
