package com.slowv.udemi.component;

import com.slowv.udemi.web.rest.errors.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class RetryComponent {

    @SneakyThrows
    public <T> T retry(Supplier<T> supplier, int maxAttempts, int delay) {
        int tries = 0;
        for (int i = 0; i < maxAttempts; i++) {
            try {
                log.info("Retrying attempt {} of {}", tries + 1, maxAttempts);
                return supplier.get();
            } catch (Exception ex) {
                if (tries == maxAttempts) {
                    throw ex;
                }
                tries++;
                Thread.sleep(delay);
            }
        }
        throw new BusinessException("Max attempts exceeded");
    }
}
