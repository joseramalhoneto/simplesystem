package com.simplesystem.service;

import com.simplesystem.exception.InternalServerException;
import com.simplesystem.exception.ResourceNotFoundException;
import com.simplesystem.exception.TaskException;
import com.simplesystem.model.StatusType;
import com.simplesystem.model.Task;
import com.simplesystem.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        if(!isTaskValid(task)){
            logger.info("@TaskService/saveTask/isTaskValid/TaskException/error 401");
            throw new TaskException(("Task is not valid. Please, try again."));
        }else{
            logger.info("@TaskService/saveTask");
            return taskRepository.save(task);
        }
    }

    public List<Task> findAllTasks() {
        logger.info("@TaskService/findAllTasks");
        return taskRepository.findAll();
    }

    public Task findTaskById(Long TaskId) {
        logger.info("@TaskService/findTaskById");
        return taskRepository
                .findById(TaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + TaskId));  //handling for error 404
    }

    public List<Task> findAllTasksByStatus(StatusType status) {
        logger.info("@TaskService/findTaskByStatus");
        return taskRepository.findByStatus(status);
    }

    public void deleteTaskById(Long id){
        logger.info("@TaskService/deleteTaskById");
        taskRepository
                .findById(id)
                .orElseThrow(() -> new InternalServerException("Task not found with id: " + id));   //handling for error 500

        taskRepository.deleteById(id);
    }

    private boolean isTaskValid(Task task) {
        return Pattern.compile("^[a-zA-Z0-9+\\s]*$")
                .matcher(task.getDescription())
                .matches();
    }

    public void updateAllTask() {
        logger.info("@TaskService/updateAllTask");
        taskRepository.findAll()
                .stream()
                .filter(task -> task.getDue().isBefore(LocalDateTime.now()))
                .forEach(task -> {
                         task.setStatus(StatusType.PastDue);
                         taskRepository.save(task);
                    });
    }
}
