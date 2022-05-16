package com.simplesystem.controller;

import com.simplesystem.model.StatusType;
import com.simplesystem.model.Task;
import com.simplesystem.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody Task task){
        logger.info("@PostMapping/saveTask");
        return new ResponseEntity<>(
                taskService.saveTask(task),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Task>> findAllTasks(){
        logger.info("@GetMapping/findAllTasks");
        return new ResponseEntity<>(
                taskService.findAllTasks(),
                HttpStatus.OK
        );
    }

    @GetMapping("/find/{id}")
    public Task findTaskById(@PathVariable("id") Long taskId) {
        logger.info("@GetMapping/findTaskById");
        return taskService.findTaskById(taskId);
    }

    @GetMapping("/find/allByStatus/{status}")
    public ResponseEntity<List<Task>> findAllTasksByStatus(@PathVariable("status") StatusType status){
        logger.info("@GetMapping/findAllTasksByStatus");
        return new ResponseEntity<>(
                taskService.findAllTasksByStatus(status),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        logger.info("@PutMapping/updateTask");
        return new ResponseEntity<>(
                taskService.saveTask(task), HttpStatus.OK
        );
    }

    @PutMapping("/update/all")
    public ResponseEntity<?> updateAllTask(){
        logger.info("@PutMapping/updateAllTask");
        taskService.updateAllTask();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id") Long id){
        logger.info("@DeleteMapping/deleteTaskById");
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
