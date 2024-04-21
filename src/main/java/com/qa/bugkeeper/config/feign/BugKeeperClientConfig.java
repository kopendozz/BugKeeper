package com.qa.bugkeeper.config.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class BugKeeperClientConfig {

    @Bean
    public RequestInterceptor bugKeeperRequestInterceptor() {
        return new BugKeeperRequestInterceptor();
    }
}
