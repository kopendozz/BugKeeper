package com.qa.bugkeeper.mapper;

import com.qa.bugkeeper.dto.IssueDto;
import com.qa.bugkeeper.entity.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    Issue toEntity(IssueDto dto);
}
