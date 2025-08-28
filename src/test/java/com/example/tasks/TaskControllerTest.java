package com.example.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("GET /api/tasks returns 200 and list")
    void getAllTasks() throws Exception {
        when(taskService.getAll()).thenReturn(List.of(
                new Task(1L, "A", false),
                new Task(2L, "B", true)
        ));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} returns 200 when found")
    void getById_found() throws Exception {
        when(taskService.getById(1L)).thenReturn(Optional.of(new Task(1L, "A", false)));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("A"));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} returns 404 when missing")
    void getById_missing() throws Exception {
        when(taskService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task 99 not found"));
    }

    @Test
    @DisplayName("POST /api/tasks creates and returns 201")
    void createTask() throws Exception {
        when(taskService.add(any(Task.class))).thenReturn(new Task(10L, "New Task", false));

        String body = "{\"title\":\"New Task\",\"completed\":false}";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    @DisplayName("POST /api/tasks validates title is required")
    void createTask_validation() throws Exception {
        String body = "{\"title\":\"\",\"completed\":false}";

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("title is required"));
    }
}
