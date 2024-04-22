package com.qa.bugkeeper.google;

import com.qa.bugkeeper.issue.Issue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.qa.bugkeeper.constant.BugKeeperConstants.TEXT;

@Service
@RequiredArgsConstructor
public class GoogleTranslateService {
    
    public static final String ENGLISH_LANGUAGE_CODE = "en";
    
    private final GoogleTranslateClient googleTranslateClient;
    
    public Issue translateIssue(Issue issue) {
        var title = issue.getName();
        var sourceLanguage = detectLanguage(title);
        
        if (StringUtils.equals(ENGLISH_LANGUAGE_CODE, sourceLanguage)) return issue;

        var translatedTitle = translate(title, sourceLanguage);
        var translatedDescription = translate(issue.getDescription(), sourceLanguage);
        issue.setName(translatedTitle);
        issue.setDescription(translatedDescription);
        return issue;
    }
    
    private String detectLanguage(String text) {
        return googleTranslateClient.detectLanguage(text)
                .getData().getDetections().getFirst().getFirst().getLanguage();
    }
    
    private String translate(String text, String sourceLanguage) {
        return googleTranslateClient.translate(text, ENGLISH_LANGUAGE_CODE, sourceLanguage, TEXT)
                .getData().getTranslations().getFirst().getTranslatedText();
    }
}
