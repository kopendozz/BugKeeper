package com.qa.bugkeeper.mapper;

import com.qa.bugkeeper.dto.UserDto;
import com.qa.bugkeeper.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto dto);
}
