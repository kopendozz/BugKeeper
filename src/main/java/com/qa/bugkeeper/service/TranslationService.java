package com.qa.bugkeeper.service;

import com.qa.bugkeeper.client.GoogleTranslateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.qa.bugkeeper.constant.BugKeeperConstants.TEXT;

@Service
@RequiredArgsConstructor
public class TranslationService {

    public static final String ENGLISH_LANGUAGE_CODE = "en";

    private final GoogleTranslateClient googleTranslateClient;

    public String detectLanguage(String text) {
        return googleTranslateClient.detectLanguage(text)
                .getData().getDetections().getFirst().getFirst().getLanguage();
    }

    public String translate(String text, String sourceLanguage) {
        return googleTranslateClient.translate(text, ENGLISH_LANGUAGE_CODE, sourceLanguage, TEXT)
                .getData().getTranslations().getFirst().getTranslatedText();
    }
}
