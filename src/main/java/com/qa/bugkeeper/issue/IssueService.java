package com.qa.bugkeeper.issue;

import com.qa.bugkeeper.google.GoogleTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IssueService {
    
    private final GoogleTranslateService googleTranslateService;
    private final IssueRepository issueRepository;
    
    public void save(Issue issue) {
        var now = LocalDateTime.now();
        
        if (Objects.isNull(issue.getId())) {
            issue.setCreatedAt(now);
            issue = googleTranslateService.translateIssue(issue);
        }
        
        issue.setLastUpdated(now);
        issueRepository.save(issue);
    }
}
