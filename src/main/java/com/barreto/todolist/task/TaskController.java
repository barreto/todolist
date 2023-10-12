package com.barreto.todolist.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public TaskModel create(@RequestBody TaskModel taskModel) throws JsonProcessingException {
        System.out.println("Hi from Controller!");
        return taskRepository.save(taskModel);
    }
}
