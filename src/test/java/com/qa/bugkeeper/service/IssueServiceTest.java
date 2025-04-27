package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.IssueDto;
import com.qa.bugkeeper.entity.Issue;
import com.qa.bugkeeper.mapper.IssueMapper;
import com.qa.bugkeeper.repository.IssueRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.qa.bugkeeper.service.TranslationService.ENGLISH_LANGUAGE_CODE;
import static org.mockito.Mockito.*;

class IssueServiceTest extends BaseServiceTest {

    @Mock
    IssueRepository issueRepository;

    @Mock
    IssueMapper issueMapper;

    @Mock
    TranslationService translationService;

    @InjectMocks
    IssueService issueService;

    @Nested
    class CreateIssue {

        @Test
        void shouldTranslateFieldsAndSaveIssue_whenValidIssueDtoProvided() {
            //given
            var translatedName = "Translated project name";
            var translatedDescription = "Translated project description";
            var issueDto = IssueDto.builder()
                    .name("Project name")
                    .description("Project description")
                    .build();
            var issueEntity = Issue.builder()
                    .name(translatedName)
                    .description(translatedDescription)
                    .build();
            when(translationService.translate(issueDto.getName(), ENGLISH_LANGUAGE_CODE)).thenReturn(translatedName);
            when(translationService.translate(issueDto.getDescription(), ENGLISH_LANGUAGE_CODE)).thenReturn(translatedDescription);
            when(issueMapper.toEntity(issueDto)).thenReturn(issueEntity);

            //when
            issueService.save(issueDto);

            //then
            verify(issueRepository).save(argThat(issue ->
                    issue.getName().equals(translatedName) &&
                            issue.getDescription().equals(translatedDescription)));
            verifyNoMoreInteractions(issueRepository, issueMapper, translationService);
        }
    }
}
