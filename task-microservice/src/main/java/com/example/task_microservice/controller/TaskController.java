package com.example.task_microservice.controller;

import com.example.task_microservice.dto.TaskStatusUpdateDto;
import com.example.task_microservice.model.Person;
import com.example.task_microservice.model.Task;
import com.example.task_microservice.model.TaskStatus;
import com.example.task_microservice.service.SenderService;
import com.example.task_microservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final SenderService senderService;

    public TaskController(TaskService taskService , SenderService senderService) {
        this.taskService = taskService;
        this.senderService = senderService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
//
//    @GetMapping
//    public List<Task> getAllTasks() {
//        return taskService.getAllTasks();
//    }

    @PostMapping("/{id}")
    public Task addTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @PutMapping("/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id, TaskStatusUpdateDto dto) {
        Task updatedTask = taskService.updateTaskStatus(id, dto.getStatus());

        Long projectId = updatedTask.getProjectId();
        String completion = taskService.getProjectCompletionPercentage(projectId);
        String message = String.format(
                "Task updated: \"%s\" with status %s. Project ID: %d. Completion: %s.",
                updatedTask.getDescription(),
                updatedTask.getStatus(),
                projectId,
                completion
        );
        senderService.sendMessage(message);

        return updatedTask;
    }

    @PutMapping("/{id}")
    public Task editTask(@PathVariable("id") Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);

        Long projectId = updatedTask.getProjectId();
        String completion = taskService.getProjectCompletionPercentage(projectId);

        String message = String.format(
                "Task updated: \"%s\". Project ID: %d. Completion: %s",
                updatedTask.getDescription(),
                projectId,
                completion
        );
        senderService.sendMessage(message);

        return updatedTask;
    }

    @GetMapping("/project/{projectId}/assignments")
    public Map<Task, List<Person>> getTaskAssignments(@PathVariable Long projectId) {
        return taskService.getTaskAssignmentsByProjectId(projectId);
    }

    @GetMapping("/project/{projectId}/completion")
    public String getProjectCompletion(@PathVariable Long projectId) {

        return taskService.getProjectCompletionPercentage(projectId);
    }
}