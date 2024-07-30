package com.example.graphic_system.services;

import com.example.graphic_system.models.Project;
import com.example.graphic_system.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setBudget(projectDetails.getBudget());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setGoals(projectDetails.getGoals());

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
