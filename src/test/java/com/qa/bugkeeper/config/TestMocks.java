package com.qa.bugkeeper.config;

import com.qa.bugkeeper.service.ProjectService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestMocks {

    @Bean
    public ProjectService projectService() {
        return mock(ProjectService.class);
    }
}
