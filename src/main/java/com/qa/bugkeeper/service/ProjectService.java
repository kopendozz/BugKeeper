package com.qa.bugkeeper.service;

import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    public static final String PROJECT_NOT_FOUND = "Project not found: ";
    private final ProjectRepository projectRepository;

    public Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(PROJECT_NOT_FOUND + projectId));
    }
}
