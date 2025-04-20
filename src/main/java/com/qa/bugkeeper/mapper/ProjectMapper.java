package com.qa.bugkeeper.mapper;

import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    List<ProjectDto> toDtoList(List<Project> projects);
}
