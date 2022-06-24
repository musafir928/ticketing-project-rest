package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<ResponseWrapper> getAllTasks() {
        return ResponseEntity.ok(
                new ResponseWrapper(
                        "successfully got all tasks",
                        taskService.listAllTasks(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                new ResponseWrapper(
                        "successfully got task for id: " + id,
                        taskService.findById(id),
                        HttpStatus.OK
                )
        );
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper(
                                "task successfully created",
                                HttpStatus.CREATED
                        )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "task successfully deleted",
                                HttpStatus.OK
                        )
                );
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "task successfully updated",
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks() {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got pending tasks for employee",
                                taskService.listAllTasksByStatusIsNot(Status.COMPLETE),
                                HttpStatus.OK
                        )
                );
    }

    @PutMapping("/employee/update")
    public ResponseEntity<ResponseWrapper> employeeUpdateTaskStatus(@RequestBody TaskDTO taskDTO) {
        taskService.updateStatus(taskDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "task successfully updated",
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks() {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got completed tasks for employee",
                                taskService.listAllTasksByStatus(Status.COMPLETE),
                                HttpStatus.OK
                        )
                );
    }
}
