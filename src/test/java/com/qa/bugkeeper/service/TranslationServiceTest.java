package com.qa.bugkeeper.service;

import com.qa.bugkeeper.client.GoogleTranslateClient;
import com.qa.bugkeeper.dto.DetectedLanguagesList;
import com.qa.bugkeeper.dto.TranslationsList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static com.qa.bugkeeper.constant.BugKeeperConstants.TEXT;
import static com.qa.bugkeeper.service.TranslationService.ENGLISH_LANGUAGE_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TranslationServiceTest extends BaseServiceTest {

    @Mock
    GoogleTranslateClient googleTranslateClient;

    @InjectMocks
    TranslationService translationService;

    @Nested
    class DetectLanguage {

        @Test
        void shouldReturnDetectedLanguage_whenValidTextProvided() {
            //given
            var text = "text";
            var expected = "EN";
            var detectedLanguage = DetectedLanguagesList.builder()
                    .data(DetectedLanguagesList.Data.builder().detections(List.of(List.of(
                            DetectedLanguagesList.Detection.builder()
                                    .language(expected)
                                    .build()))).build())
                    .build();
            when(googleTranslateClient.detectLanguage(text)).thenReturn(detectedLanguage);

            //when
            var result = translationService.detectLanguage(text);

            //then
            assertThat(result)
                    .as("Detected language is not as expected.")
                    .isEqualTo(expected);
            verify(googleTranslateClient).detectLanguage(text);
            verifyNoMoreInteractions(googleTranslateClient);
        }
    }

    @Nested
    class Translate {

        @Test
        void shouldReturnTranslatedText_whenValidTextAndTargetLanguageProvided() {
            //given
            var text = "raw_text";
            var translatedText = "translated_text";
            var translationsList = TranslationsList.builder()
                    .data(TranslationsList.Data.builder()
                            .translations(List.of(TranslationsList.Translation.builder()
                                    .translatedText(translatedText)
                                    .build()))
                            .build())
                    .build();
            when(googleTranslateClient.translate(text, ENGLISH_LANGUAGE_CODE, ENGLISH_LANGUAGE_CODE, TEXT))
                    .thenReturn(translationsList);

            //when
            var result = translationService.translate(text, ENGLISH_LANGUAGE_CODE);

            //then
            assertThat(result)
                    .as("Translated text is not as expected.")
                    .isEqualTo(translatedText);
            verify(googleTranslateClient).translate(text, ENGLISH_LANGUAGE_CODE, ENGLISH_LANGUAGE_CODE, TEXT);
            verifyNoMoreInteractions(googleTranslateClient);
        }
    }
}
