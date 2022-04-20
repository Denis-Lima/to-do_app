package br.com.todoserver.todoapp.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean finished;
}
