package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.mapper.ProjectMapper;
import com.qa.bugkeeper.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectService projectService;

    @Nested
    class FindProject {

        @Test
        @DisplayName("should return DTO when project exists")
        void shouldReturnProjectDto() {
            // given
            var project = new Project();
            project.setId(1L);
            project.setName("Beta");

            var dto = new ProjectDto(1L, "Beta");

            when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
            when(projectMapper.toDto(project)).thenReturn(dto);

            // when
            var result = projectService.findProject(1L);

            // then
            assertThat(result).isEqualTo(dto);
            verify(projectRepository).findById(1L);
            verify(projectMapper).toDto(project);
        }

        @Test
        @DisplayName("should throw when project not found")
        void shouldThrowWhenProjectNotFound() {
            // given
            when(projectRepository.findById(42L)).thenReturn(Optional.empty());

            // when / then
            assertThatThrownBy(() -> projectService.findProject(42L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Project not found: 42");

            verify(projectRepository).findById(42L);
            verifyNoInteractions(projectMapper);
        }
    }

    @Nested
    class GetAllProjects {

        @Test
        @DisplayName("should return all mapped DTOs")
        void shouldReturnAllProjectDtos() {
            // given
            var project = new Project();
            project.setId(1L);
            project.setName("Alpha");

            var dto = new ProjectDto(1L, "Alpha");

            when(projectRepository.findAll()).thenReturn(List.of(project));
            when(projectMapper.toDtoList(List.of(project))).thenReturn(List.of(dto));

            // when
            var result = projectService.getAllProjects();

            // then
            assertThat(result).containsExactly(dto);
            verify(projectRepository).findAll();
            verify(projectMapper).toDtoList(any());
        }
    }
}
