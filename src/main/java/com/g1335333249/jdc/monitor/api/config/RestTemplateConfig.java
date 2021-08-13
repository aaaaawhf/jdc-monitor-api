package com.g1335333249.jdc.monitor.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate(){
//        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        final HttpClient httpClient = HttpClientBuilder.create()
//                .setRedirectStrategy(new LaxRedirectStrategy())
//                .build();
//        factory.setHttpClient(httpClient);
//        restTemplate.setRequestFactory(factory);
        return restTemplateBuilder.build();
    }
}
