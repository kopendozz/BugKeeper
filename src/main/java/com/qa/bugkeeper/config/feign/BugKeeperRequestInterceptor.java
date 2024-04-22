package com.qa.bugkeeper.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class BugKeeperRequestInterceptor implements RequestInterceptor {
    
    @Value("${google.translate.api-key}")
    private String apiKey;
    
    @Override
    public void apply(RequestTemplate template) {
        template.query("key", apiKey);
    }
}
