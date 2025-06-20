package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Endpoints for managing projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "/projects")
    @Operation(
            summary = "Get all projects",
            description = "Returns a list of all projects in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all projects",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/projects")
    @Operation(
            summary = "Create a project",
            description = "Creates a new project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project created"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public void createProject(@RequestBody ProjectDto projectDto) {
        projectService.createProject(projectDto);
    }

    @PutMapping("/projects/{id}")
    @Operation(
            summary = "Update a project",
            description = "Updates an existing project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project updated"),
                    @ApiResponse(responseCode = "404", description = "Project not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public void updateProject(@PathVariable("id") Long id, @RequestBody ProjectDto projectDto) {
        projectService.updateProject(id, projectDto);
    }

    @DeleteMapping("/projects/{id}")
    @Operation(
            summary = "Delete a project",
            description = "Deletes a project by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project deleted"),
                    @ApiResponse(responseCode = "404", description = "Project not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public void deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
    }
}
