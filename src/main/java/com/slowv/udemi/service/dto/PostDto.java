package com.slowv.udemi.service.dto;

import lombok.Data;

@Data
public class PostDto {
    private int id;
    private String title;
    private String body;
    private int userId;
}
