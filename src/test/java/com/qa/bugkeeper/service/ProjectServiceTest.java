package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.entity.Project;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.mapper.ProjectMapper;
import com.qa.bugkeeper.repository.ProjectRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.qa.bugkeeper.service.ProjectService.PROJECT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProjectServiceTest extends BaseServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectService projectService;

    @Nested
    class FindProject {

        @Test
        void shouldReturnProjectDto() {
            // given
            var id = 1L;
            var name = "Beta";
            var project = Project.builder()
                    .id(id)
                    .name(name)
                    .build();
            var dto = ProjectDto.builder()
                    .id(id)
                    .name(name)
                    .build();

            when(projectRepository.findById(id)).thenReturn(Optional.of(project));
            when(projectMapper.toDto(project)).thenReturn(dto);

            // when
            var result = projectService.findProject(id);

            // then
            assertThat(result).isEqualTo(dto);
            verify(projectRepository).findById(id);
            verify(projectMapper).toDto(project);
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }

        @Test
        void shouldThrowResourceNotFoundException_whenProjectNotFound() {
            // given
            var id = 42L;
            when(projectRepository.findById(id)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> projectService.findProject(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(PROJECT_NOT_FOUND + id);

            verify(projectRepository).findById(id);
            verifyNoInteractions(projectMapper);
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }
    }

    @Nested
    class GetAllProjects {

        @Test
        void shouldReturnAllProjectDtos() {
            // given
            var id = 1L;
            var name = "Alpha";
            var project = Project.builder()
                    .id(id)
                    .name(name)
                    .build();
            var dto = ProjectDto.builder()
                    .id(id)
                    .name(name)
                    .build();

            when(projectRepository.findAll()).thenReturn(List.of(project));
            when(projectMapper.toDtoList(List.of(project))).thenReturn(List.of(dto));

            // when
            var result = projectService.getAllProjects();

            // then
            assertThat(result).containsExactly(dto);
            verify(projectRepository).findAll();
            verify(projectMapper).toDtoList(List.of(project));
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }

        @Test
        void shouldReturnEmptyList_whenNoProjectsExist() {
            // given
            when(projectRepository.findAll()).thenReturn(List.of());
            when(projectMapper.toDtoList(List.of())).thenReturn(List.of());

            // when
            var result = projectService.getAllProjects();

            // then
            assertThat(result).isEmpty();
            verify(projectRepository).findAll();
            verify(projectMapper).toDtoList(List.of());
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }
    }
}
