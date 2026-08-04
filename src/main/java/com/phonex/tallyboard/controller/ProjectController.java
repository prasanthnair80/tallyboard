package com.phonex.tallyboard.controller;

import com.phonex.tallyboard.model.Project;
import com.phonex.tallyboard.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable String id) {
        return projectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
