package br.com.todoserver.todoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoserver.todoapp.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    public List<ProjectEntity> findByUser_Id(Long id);
}
