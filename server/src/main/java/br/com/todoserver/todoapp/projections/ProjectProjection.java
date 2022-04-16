package br.com.todoserver.todoapp.projections;

import java.time.LocalDateTime;

public interface ProjectProjection {
    public interface WithoutUser {
        Long getId();
        String getName();
        LocalDateTime getCreatedAt();
    }
}
