package com.example.todolist.task;

import com.example.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    @Autowired
    final TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
        task.setUserId((UUID) request.getAttribute("userId"));

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStartsAt()) || currentDate.isAfter(task.getEndsAt()) || task.getStartsAt().isAfter(task.getEndsAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid date");
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(task));
    }

    @GetMapping
    public List<Task> getTasksByUserId(HttpServletRequest request) {
        return taskRepository.findByUserId((UUID) request.getAttribute("userId"));
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@RequestBody Task newTask, @PathVariable UUID id, HttpServletRequest request) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task no found");
        }

        if (!task.getUserId().equals((UUID) request.getAttribute("userId"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("you cant updated a task that you are not the owner");
        }

        Utils.copyNonNullProperties(newTask, task);
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }
}
