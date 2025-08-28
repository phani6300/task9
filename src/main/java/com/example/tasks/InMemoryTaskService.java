package com.example.tasks;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryTaskService implements TaskService {
    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    public InMemoryTaskService() {
        // seed with one task
        Task seed = new Task(seq.incrementAndGet(), "Learn JUnit", false);
        store.put(seed.getId(), seed);
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Task> getById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Task add(Task t) {
        long id = seq.incrementAndGet();
        Task saved = new Task(id, t.getTitle(), t.isCompleted());
        store.put(id, saved);
        return saved;
    }
}
