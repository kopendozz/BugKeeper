package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.mapper.ProjectMapper;
import com.qa.bugkeeper.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    public static final String PROJECT_NOT_FOUND = "Project not found: ";
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectDto> getAllProjects() {
        var projects = projectRepository.findAll();
        return projectMapper.toDtoList(projects);
    }

    public ProjectDto findProject(Long projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(PROJECT_NOT_FOUND + projectId));
        return projectMapper.toDto(project);
    }
}
