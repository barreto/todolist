package com.barreto.todolist.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public TaskModel create(@RequestBody TaskModel taskModel, HttpServletRequest request) throws JsonProcessingException {
        System.out.println("Hi from Controller!");
        var idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);
        return taskRepository.save(taskModel);
    }
}
