package com.example.task_microservice.repository;

import com.example.task_microservice.model.Person;
import com.example.task_microservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
