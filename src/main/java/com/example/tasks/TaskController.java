package com.example.tasks;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Task byId(@PathVariable Long id) {
        return service.getById(id).orElseThrow(() -> new NotFoundException("Task %d not found".formatted(id)));
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody Task t) {
        Task saved = service.add(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
