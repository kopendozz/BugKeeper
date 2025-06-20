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

    @Nested
    class CreateProject {

        @Test
        void shouldSaveProject() {
            var dto = ProjectDto.builder().name("Alpha").build();
            var entity = Project.builder().name("Alpha").build();

            when(projectMapper.toEntity(dto)).thenReturn(entity);

            projectService.createProject(dto);

            verify(projectMapper).toEntity(dto);
            verify(projectRepository).save(entity);
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }
    }

    @Nested
    class UpdateProject {

        @Test
        void shouldUpdateExistingProject() {
            var id = 1L;
            var existing = Project.builder().id(id).name("Old").build();
            var dto = ProjectDto.builder().name("New").build();

            when(projectRepository.findById(id)).thenReturn(Optional.of(existing));

            projectService.updateProject(id, dto);

            verify(projectRepository).findById(id);
            verify(projectRepository).save(argThat(p -> p.getId().equals(id) && p.getName().equals("New")));
            verifyNoMoreInteractions(projectRepository, projectMapper);
        }

        @Test
        void shouldThrow_whenProjectNotFound() {
            var id = 42L;
            var dto = ProjectDto.builder().name("name").build();
            when(projectRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> projectService.updateProject(id, dto))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(PROJECT_NOT_FOUND + id);

            verify(projectRepository).findById(id);
            verifyNoMoreInteractions(projectRepository);
            verifyNoInteractions(projectMapper);
        }
    }

    @Nested
    class DeleteProject {

        @Test
        void shouldDelete_whenExists() {
            var id = 1L;
            when(projectRepository.existsById(id)).thenReturn(true);

            projectService.deleteProject(id);

            verify(projectRepository).existsById(id);
            verify(projectRepository).deleteById(id);
            verifyNoMoreInteractions(projectRepository);
        }

        @Test
        void shouldThrow_whenProjectMissing() {
            var id = 42L;
            when(projectRepository.existsById(id)).thenReturn(false);

            assertThatThrownBy(() -> projectService.deleteProject(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(PROJECT_NOT_FOUND + id);

            verify(projectRepository).existsById(id);
            verifyNoMoreInteractions(projectRepository);
        }
    }
}
