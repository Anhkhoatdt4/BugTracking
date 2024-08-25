package com.devteria.bugtracking.service;


import com.devteria.bugtracking.entity.Project;
import com.devteria.bugtracking.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    ProjectRepository projectRepository;

    public Project createProject(Project project) {
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            log.error("Error creating project: {}", e.getMessage());
            throw new RuntimeException("Error creating project", e);
        }
    }

    public Project updateProject(Project project) {
        if (!projectRepository.existsById(project.getId())) {
            throw new RuntimeException("Project not found");
        }
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
