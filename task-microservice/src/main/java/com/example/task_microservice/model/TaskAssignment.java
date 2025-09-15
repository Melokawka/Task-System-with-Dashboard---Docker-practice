package com.example.task_microservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "task_assignments")
public class TaskAssignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    public TaskAssignment() {}

    public TaskAssignment(Task task, Person person, LocalDate assignedDate) {
        this.task = task;
        this.person = person;
        this.assignedDate = assignedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
