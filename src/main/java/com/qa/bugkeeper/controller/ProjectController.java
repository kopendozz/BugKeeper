package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
