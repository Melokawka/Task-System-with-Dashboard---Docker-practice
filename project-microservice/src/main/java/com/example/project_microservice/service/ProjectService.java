package com.example.project_microservice.service;

import com.example.project_microservice.RabbitMQConfig;
import com.example.project_microservice.model.Project;
import com.example.project_microservice.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}