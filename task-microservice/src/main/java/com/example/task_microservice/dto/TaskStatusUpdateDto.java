package com.example.task_microservice.dto;

import com.example.task_microservice.model.TaskStatus;

public class TaskStatusUpdateDto {
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}