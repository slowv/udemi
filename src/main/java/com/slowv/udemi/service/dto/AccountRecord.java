package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountRecord(
        String email,
        TokenRecord accessToken,
//        TokenRecord refreshToken,
        AccountInfoRecord accountInfo,
        List<String> roleNames
) {
}
