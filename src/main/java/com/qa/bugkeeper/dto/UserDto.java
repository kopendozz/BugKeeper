package com.qa.bugkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private Boolean enabled;
}
