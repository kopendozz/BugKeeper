package com.qa.bugkeeper.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project data transfer object representing a project")
public class ProjectDto {

    @Schema(description = "The ID of the project", example = "1")
    private Long id;

    @Schema(description = "The name of the project", example = "Project X")
    private String name;
}
