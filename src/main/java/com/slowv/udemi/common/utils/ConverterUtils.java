package com.slowv.udemi.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterUtils {

    public static byte[] readBytesFromInputStream(InputStream is, int length) {
        try {
            final var output = new ByteArrayOutputStream();
            final var bytes = new byte[length];
            int nRead;
            while ((nRead = is.read(bytes)) != -1) {
                output.write(bytes, 0, nRead);
            }
            return output.toByteArray();
        } catch (Exception ignore) {
            return new byte[0];
        }
    }
}
