package com.qa.bugkeeper.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Detection {
    
    private Boolean isReliable;
    private Integer confidence;
    private String language;
}
