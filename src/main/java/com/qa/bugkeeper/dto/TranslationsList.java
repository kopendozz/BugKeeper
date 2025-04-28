package com.qa.bugkeeper.dto;

import lombok.*;

import java.util.List;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationsList {
    
    private Data data;

    @lombok.Data
    @Builder
    public static class Data {
        
        private List<Translation> translations;
    }

    @lombok.Data
    @Builder
    public static class Translation {

        private String translatedText;
    }
}
