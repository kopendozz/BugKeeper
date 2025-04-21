package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "/projects")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }
}
