package com.slowv.udemi.service.dto;

import java.io.Serializable;

/**
 * Record for {@link com.slowv.course.entity.AccountInfoEntity}
 */
public record AccountInfoRecord(String firstName, String lastName, String introduce,
                                String phone) implements Serializable {
}