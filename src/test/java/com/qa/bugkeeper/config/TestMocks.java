package com.qa.bugkeeper.config;

import com.qa.bugkeeper.service.IssueService;
import com.qa.bugkeeper.service.ProjectService;
import com.qa.bugkeeper.service.TranslationService;
import com.qa.bugkeeper.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestMocks {

    @Bean
    public ProjectService projectService() {
        return mock(ProjectService.class);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public TranslationService translationService() {
        return mock(TranslationService.class);
    }

    @Bean
    public IssueService issueService() {
        return mock(IssueService.class);
    }
}
