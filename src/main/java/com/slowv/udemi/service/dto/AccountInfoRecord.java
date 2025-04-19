package com.slowv.udemi.service.dto;

import java.io.Serializable;

public record AccountInfoRecord(String firstName, String lastName, String introduce,
                                String phone,
                                String avatarUrl
) implements Serializable {
}