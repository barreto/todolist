package com.barreto.todolist.task;

public enum TaskPriority {
    EMERGENCY(1),
    CRITICAL(2),
    HIGH(3),
    NORMAL(4),
    LOW(5);

    TaskPriority(int level) {
    }
}
