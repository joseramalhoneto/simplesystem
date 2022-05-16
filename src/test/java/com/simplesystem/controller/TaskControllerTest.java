package com.simplesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesystem.model.StatusType;
import com.simplesystem.model.Task;
import com.simplesystem.service.TaskService;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    private Task task1, task2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Before
    public void setUp() {
        task1  = new Task(100L, "Task description 01", StatusType.NotDone, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        task2  = new Task(200L, "Task description 02", StatusType.NotDone, LocalDateTime.now(), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3));
    }

    @Test
    public void saveTask() throws Exception {
        when(taskService.saveTask(any()))
                .thenReturn(task1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/task/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(task1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskId").value(100L))
                .andExpect(jsonPath("$.description").value("Task description 01"));
    }

    @Test
    public void findAllTasks() throws Exception {
        when(taskService
                .findAllTasks())
                .thenReturn(List.of(task1, task2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/task/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].taskId").value(100L))
                .andExpect(jsonPath("$[1].taskId").value(200L));
    }

    @Test
    public void findTaskById() throws Exception {
        when(taskService
                .findTaskById(100L))
                .thenReturn(task1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/task/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(100L))
                .andExpect(jsonPath("$.description").value("Task description 01"));
    }

    @Test
    public void findAllTasksByStatus() throws Exception {
        when(taskService
                .findAllTasksByStatus(StatusType.NotDone))
                .thenReturn(List.of(task1, task2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/task/find/allByStatus/{status}", StatusType.NotDone))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].taskId").value(100L))
                .andExpect(jsonPath("$[1].taskId").value(200L));
    }

    @Test
    public void updateAllTask() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/task/update/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskById() throws Exception {
        this.mockMvc.perform(delete("/task/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}