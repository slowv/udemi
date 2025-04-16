package com.slowv.udemi.channel.impl;

import com.slowv.udemi.channel.HttpChannel;
import com.slowv.udemi.channel.ResourceServiceChannel;
import com.slowv.udemi.component.RetryComponent;
import feign.RequestTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceChannelImpl extends HttpChannel implements ResourceServiceChannel {

    @Value("${channel.resource-service.api.resource}")
    private String apiGetResource;

    @Getter
    private final RestTemplate restTemplate;

    private final RetryComponent retryComponent;

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2),
            retryFor = {RuntimeException.class}
    )
    @Override
    public String getResource() {
        // retry bằng cơm
//        return retryComponent.retry(
//                () -> restTemplate.getForEntity(apiGetResource, String.class).getBody(),
//                3,
//                2000
//        );
        log.info("CALL API getResource from Resource Service Channel");

        final var headers = new HttpHeaders();
        headers.add("User-Agent", "Chrome 3.0.1");
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", this.TOKEN_PREFIX + getBearerToken());

        return this.get(
                apiGetResource,
                new ParameterizedTypeReference<>() {
                },
                headers);
    }

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        String token = getBearerToken();
        if (token != null) {
            requestTemplate.header(this.AUTHORIZATION_HEADER, this.TOKEN_PREFIX + token);
        }
    }
}
