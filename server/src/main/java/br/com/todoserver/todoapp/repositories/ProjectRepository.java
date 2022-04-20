package br.com.todoserver.todoapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.todoserver.todoapp.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    public List<ProjectEntity> findByUser_Id(Long id);

    public Optional<ProjectEntity> findByIdAndUser_Id(Long projectId, Long userId);
}
