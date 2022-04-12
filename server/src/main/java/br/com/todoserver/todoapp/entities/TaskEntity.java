package br.com.todoserver.todoapp.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "task")
public class TaskEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tas_cod")
    private Long id;

    @Column(name = "tas_name", nullable = false)
    private String name;

    @Column(name = "tas_created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "tas_updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "tas_finished")
    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "tas_use_cod")
    private UserEntity user;
}
