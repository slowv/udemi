package com.slowv.udemi.channel;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class HttpChannel {

    protected final String AUTHORIZATION_HEADER = "Authorization";
    protected final String TOKEN_PREFIX = "Bearer ";

    protected <R> R get(final String url, final ParameterizedTypeReference<R> responseType, HttpHeaders headers) {
        return exchange(url, HttpMethod.GET, null, responseType, headers);
    }

    protected <T, R> R post(final String url, T body, ParameterizedTypeReference<R> responseType, HttpHeaders headers) {
        return exchange(url, HttpMethod.POST, body, responseType, headers);
    }

    protected <T, R> R exchange(final String url, HttpMethod method, T body, ParameterizedTypeReference<R> responseType, HttpHeaders headers) {
        return getRestTemplate().exchange(url, method, new HttpEntity<>(body, headers), responseType).getBody();
    }

    protected String getBearerToken() {
        final var requestAttributes = RequestContextHolder.getRequestAttributes();

        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return null;
        }

        final var header = servletRequestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER);
        if (ObjectUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        return header.substring(TOKEN_PREFIX.length());
    }

    protected abstract RestTemplate getRestTemplate();
}
