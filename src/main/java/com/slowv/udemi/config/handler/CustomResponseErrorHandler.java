package com.slowv.udemi.config.handler;

import com.slowv.udemi.web.rest.errors.RestTemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(final URI url, final HttpMethod method, final ClientHttpResponse response) throws IOException {
        // Xử lý lỗi tại đây (log, throw exception tùy ý)
        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        log.info("Lỗi khi gọi API: {} - {}", response.getStatusCode(), body);

        if (response.getStatusCode().is4xxClientError()) {
            throw new RestTemplateException("Call Resource service bad request!!!");
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new RestTemplateException("Call Resource service unavailable!!!");
        }
    }
}
