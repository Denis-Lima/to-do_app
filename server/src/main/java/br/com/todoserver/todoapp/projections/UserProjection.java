package br.com.todoserver.todoapp.projections;

import java.util.List;

import br.com.todoserver.todoapp.entities.ProjectEntity;

public interface UserProjection {
    public interface NoPassword {
        public Long getId();
        public String getUsername();
        public String getEmail();
    }

    public interface WithProjects extends NoPassword {
        public List<ProjectEntity> getProjects();
    }
}
