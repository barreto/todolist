package com.barreto.todolist.task;

public enum TaskStatus {
    CREATED(1),
    DOING(2),
    DONE(3),
    DELETED(4),
    ARCHIVED(5);

    TaskStatus(int id) {
    }
}
