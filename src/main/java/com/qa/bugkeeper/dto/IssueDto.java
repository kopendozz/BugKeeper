package com.qa.bugkeeper.dto;

import com.qa.bugkeeper.entity.Priority;
import com.qa.bugkeeper.entity.Status;
import com.qa.bugkeeper.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {

    private Long id;
    private Priority priority;
    private Status status;
    private String name;
    private String description;
    private UserEntity reporter;
    private UserEntity responsible;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
