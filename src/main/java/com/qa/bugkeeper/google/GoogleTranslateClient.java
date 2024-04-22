package com.qa.bugkeeper.google;

import com.qa.bugkeeper.config.feign.BugKeeperClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "google-translate-client", url = "${google.translate.url}", configuration = BugKeeperClientConfig.class)
public interface GoogleTranslateClient {

    String TRANSLATE_BASE_ENDPOINT = "/language/translate/v2";
    String DETECT_ENDPOINT = TRANSLATE_BASE_ENDPOINT + "/detect";
    
    @PostMapping(value = DETECT_ENDPOINT)
    DetectedLanguagesList detectLanguage(@RequestParam("q") String q);

    @PostMapping(value = TRANSLATE_BASE_ENDPOINT)
    TranslationsList translate(@RequestParam("q") String q, @RequestParam("target") String target,
                               @RequestParam("source") String source, @RequestParam("format") String format);
}
