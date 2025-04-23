package com.slowv.udemi.web.rest;

import com.slowv.udemi.service.dto.TestKafkaRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author trinh
 * @since 4/23/2025 - 8:56 PM
 */
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class TestKafkaController {

    private final StreamBridge streamBridge;

    @GetMapping
    public String sendMessage(
            @RequestParam String name,
            @RequestParam String message
    ) {
        final var record = new TestKafkaRecord(name, message);

        if (streamBridge.send("sendTestKafka-out-0", record)) {
            return "Success";
        }

        return "Fail";
    }
}
