package com.qa.bugkeeper.dto;

import lombok.*;

import java.util.List;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetectedLanguagesList {
    
    private Data data;
    
    @lombok.Data
    @Builder
    public static class Data {
        
        private List<List<Detection>> detections;
    }

    @lombok.Data
    @Builder
    public static class Detection {

        private Boolean isReliable;
        private Integer confidence;
        private String language;
    }
}
