package com.slowv.udemi.config;

import com.slowv.udemi.config.filter.RestTemplateLoggingInterceptor;
import com.slowv.udemi.config.handler.CustomResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

    private final CustomResponseErrorHandler customResponseErrorHandler;
    private final RestTemplateLoggingInterceptor restTemplateLoggingInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        // Set timeout
        final var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 giây
        factory.setReadTimeout(5000);   // 10 giây

        // Set buffer
        final var restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // Set error handler
        restTemplate.setErrorHandler(customResponseErrorHandler);

        // Set interceptor
        restTemplate.setInterceptors(List.of(restTemplateLoggingInterceptor));

        return restTemplate;
    }
}
