package com.barreto.todolist.task;

import com.barreto.todolist.enums.Priority;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskOutDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Priority priority
) {
    public TaskOutDTO(TaskModel taskModel) {
        this(taskModel.getId(),
                taskModel.getTitle(),
                taskModel.getDescription(),
                taskModel.getStartAt(),
                taskModel.getEndAt(),
                taskModel.getPriority());
    }
}
