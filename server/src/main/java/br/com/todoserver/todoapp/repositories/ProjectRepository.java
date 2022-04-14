package br.com.todoserver.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoserver.todoapp.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    
}
