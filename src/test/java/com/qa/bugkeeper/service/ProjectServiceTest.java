package com.qa.bugkeeper.service;

import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectService projectService;

    @Test
    void shouldFindProjectIfExists() {
        // given
        Long projectId = 1L;
        Project mockProject = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(mockProject));

        // when
        Project result = projectService.findProject(projectId);

        // then
        assertNotNull(result);
        assertSame(mockProject, result);
        verify(projectRepository).findById(projectId);
    }

    @Test
    void shouldThrowExceptionWhenProjectWasNotFound() {
        // given
        Long projectId = 999L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when
        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> projectService.findProject(projectId)
        );

        //then
        assertEquals("Project not found: 999", ex.getMessage());
        verify(projectRepository).findById(projectId);
    }
}
