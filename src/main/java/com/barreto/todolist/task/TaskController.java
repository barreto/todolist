package com.barreto.todolist.task;

import com.barreto.todolist.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        var idUser = getIdUserFromRequest(request);
        taskModel.setIdUser(idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("Start and end date must be after current date.");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.badRequest().body("Start date must precede end date.");
        }

        var taskCreated = taskRepository.save(taskModel);

        var uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(taskCreated.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public List<TaskOutDTO> list(HttpServletRequest request) {
        var idUser = getIdUserFromRequest(request);
        var taskModels = taskRepository.findByIdUser(idUser);
        var taskOutDTOStream = taskModels.stream().map(TaskOutDTO::new);
        return taskOutDTOStream.toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable UUID id, HttpServletRequest request) {
        var foundedTask = taskRepository.findById(id).orElse(null);
        if (foundedTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        var idUser = getIdUserFromRequest(request);
        if (!foundedTask.getIdUser().equals(idUser)) {
            return ResponseEntity.badRequest().body("User not authorized to update this task.");
        }

        var taskOutDTO = new TaskOutDTO(foundedTask);
        return ResponseEntity.ok(taskOutDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var taskToUpdate = taskRepository.findById(id).orElse(null);
        if (taskToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        var idUser = getIdUserFromRequest(request);
        if (!taskToUpdate.getIdUser().equals(idUser)) {
            return ResponseEntity.badRequest().body("User not authorized to update this task.");
        }

        ObjectUtils.copyNonNullProperties(taskModel, taskToUpdate);

        var updatedTask = taskRepository.save(taskToUpdate);
        var taskOutDTO = new TaskOutDTO(updatedTask);
        return ResponseEntity.ok(taskOutDTO);
    }

    private UUID getIdUserFromRequest(HttpServletRequest request) {
        return (UUID) request.getAttribute("idUser");
    }
}
