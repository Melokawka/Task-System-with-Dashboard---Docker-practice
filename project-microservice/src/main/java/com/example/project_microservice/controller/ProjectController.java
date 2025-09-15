package com.example.project_microservice.controller;

import com.example.project_microservice.model.Project;
import com.example.project_microservice.service.ProjectService;
import com.example.project_microservice.service.SenderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final SenderService senderService;

    public ProjectController(ProjectService projectService, SenderService senderService) {
        this.projectService = projectService;
        this.senderService =  senderService;
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        senderService.sendMessage("lol"); // send test message
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PutMapping("/{id}/completion")
    public Project updateProjectCompletion(@PathVariable Long id, @RequestBody int completion) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setCompletion(completion);
        Project saved = projectService.saveProject(project);

        System.out.println("Saved project with completion: " + saved.getCompletion());
        return saved;
    }

}
