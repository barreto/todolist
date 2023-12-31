package com.barreto.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status = TaskStatus.CREATED;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("Title field must contains in maximum 50 characters.");
        }

        this.title = title;
    }
}
