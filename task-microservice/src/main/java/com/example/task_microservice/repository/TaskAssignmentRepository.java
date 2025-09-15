package com.example.task_microservice.repository;

import com.example.task_microservice.model.Person;
import com.example.task_microservice.model.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByTaskIdIn(List<Long> taskIds);
}
