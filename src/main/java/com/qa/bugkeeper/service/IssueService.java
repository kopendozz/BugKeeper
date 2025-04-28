package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.IssueDto;
import com.qa.bugkeeper.mapper.IssueMapper;
import com.qa.bugkeeper.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.qa.bugkeeper.service.TranslationService.ENGLISH_LANGUAGE_CODE;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final TranslationService translationService;
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public void save(IssueDto issueDto) {
        var name = translationService.translate(issueDto.getName(), ENGLISH_LANGUAGE_CODE);
        var description = translationService.translate(issueDto.getDescription(), ENGLISH_LANGUAGE_CODE);
        issueDto.setName(name);
        issueDto.setDescription(description);
        var now = LocalDateTime.now();
        issueDto.setCreatedAt(now);
        issueDto.setLastUpdated(now);
        issueRepository.save(issueMapper.toEntity(issueDto));
    }
}
