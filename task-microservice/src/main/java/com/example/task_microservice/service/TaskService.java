package com.example.task_microservice.service;

import com.example.task_microservice.model.Person;
import com.example.task_microservice.model.Task;
import com.example.task_microservice.model.TaskAssignment;
import com.example.task_microservice.model.TaskStatus;
import com.example.task_microservice.repository.TaskAssignmentRepository;
import com.example.task_microservice.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAssignmentRepository taskAssignmentRepository;

    public TaskService(TaskRepository taskRepository, TaskAssignmentRepository taskAssignmentRepository) {
        this.taskRepository = taskRepository;
        this.taskAssignmentRepository = taskAssignmentRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Cacheable("tasks")
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Cacheable("tasks")
    public List<Task> findAll() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return taskRepository.findAll();
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        return taskRepository.save(task);
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        if (tasks.isEmpty()) {
            throw new RuntimeException("No tasks found for project " + projectId);
        }
        return tasks;
    }

    public Map<Task, List<Person>> getTaskAssignmentsByProjectId(Long projectId) {
        List<Task> tasks = getTasksByProjectId(projectId);

        List<Long> taskIds = tasks.stream().map(Task::getId).toList();
        List<TaskAssignment> assignments = taskAssignmentRepository.findByTaskIdIn(taskIds);

        Map<Long, List<Person>> taskIdToPeople = assignments.stream()
                .collect(Collectors.groupingBy(
                        assignment -> assignment.getTask().getId(), // use lambda here
                        Collectors.mapping(TaskAssignment::getPerson, Collectors.toList())
                ));

        Map<Task, List<Person>> result = new HashMap<>();
        for (Task task : tasks) {
            result.put(task, taskIdToPeople.getOrDefault(task.getId(), new ArrayList<>()));
        }

        return result;
    }

    public String getProjectCompletionPercentage(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        if (tasks.isEmpty()) {
            throw new RuntimeException("No tasks found for project " + projectId);
        }

        long completedTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED) // use enum comparison
                .count();

        int percentage = (int) ((completedTasks * 100) / tasks.size());

        return percentage + "%";
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        if (updatedTask.getTitle() != null) {
            existingTask.setProjectId(updatedTask.getProjectId());
        }
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getDueDate() != null) {
            existingTask.setDueDate(updatedTask.getDueDate());
        }

        return taskRepository.save(existingTask);
    }
}