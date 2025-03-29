package com.slowv.udemi.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusResponse {
    int code;
    String message;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    final LocalDateTime timestamp = LocalDateTime.now();

    private StatusResponse setCode(final int code) {
        this.code = code;
        return this;
    }

    private StatusResponse setMessage(final String message) {
        this.message = message;
        return this;
    }

    public static StatusResponse build(int code, String message) {
        return new StatusResponse()
                .setCode(code)
                .setMessage(message);
    }
}
