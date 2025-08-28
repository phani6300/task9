package com.example.tasks;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAll();
    Optional<Task> getById(Long id);
    Task add(Task t);
}
