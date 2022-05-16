package com.simplesystem.service;

import com.simplesystem.model.StatusType;
import com.simplesystem.model.Task;
import com.simplesystem.repository.TaskRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    private Task task1, task2;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    public TaskServiceTest() {
        task1  = new Task(100L, "Task description 01", StatusType.NotDone, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        task2  = new Task(200L, "Task description 02", StatusType.NotDone, LocalDateTime.now(), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3));
    }

    @Test
    public void saveTask() {when(taskRepository.save(task1)).thenReturn(task1);
        Task result = taskService.saveTask(task1);
        assertThat(result).isEqualTo(task1);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    public void findAllTasks() {
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);

        when(taskRepository.findAll()).thenReturn(list);
        List<Task> result = taskService.findAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void findTaskById() {
        when(taskRepository.findById(task1.getTaskId())).thenReturn(Optional.of(task1));
        Task taskReturned = taskService.findTaskById(task1.getTaskId());

        assertThat(taskReturned).isEqualTo(task1);
        assertEquals(100L, taskReturned.getTaskId().longValue());
        assertEquals("Task description 01", taskReturned.getDescription());
        assertEquals(StatusType.NotDone, taskReturned.getStatus());
        verify(taskRepository, times(1)).findById(taskReturned.getTaskId());
    }

    @Test
    public void findAllTasksByStatus() {
        when(taskRepository.findByStatus(StatusType.NotDone)).thenReturn(List.of(task1, task2));
        List<Task> tasks = taskService.findAllTasksByStatus(StatusType.NotDone);

        assertThat(tasks).isEqualTo(List.of(task1, task2));
        assertEquals(100L, tasks.get(0).getTaskId());
        assertEquals("Task description 01", tasks.get(0).getDescription());
        assertEquals(StatusType.NotDone, tasks.get(0).getStatus());
        verify(taskRepository, times(1)).findByStatus(StatusType.NotDone);
    }

}