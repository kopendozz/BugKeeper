package com.qa.bugkeeper.google;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TranslationsList {
    
    private Data data;
    
    @Getter
    @Setter
    public static class Data {
        
        private List<Translation> translations;
    }
}
