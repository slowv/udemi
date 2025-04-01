package com.slowv.udemi.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class StringUtils {
    private static final Random RANDOM = new Random();

    public String generateRandomString(int length) {
        var characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        var result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }
}
