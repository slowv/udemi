package com.slowv.udemi.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechSkill {
    JAVA("Java"),
    C_SHARP("C#"),
    JAVASCRIPT("Javascript"),
    CSS("Css"),
    SCSS("Scss"),
    HTML("HTML"),
    SPRING_BOOT("Spring Boot"),
    POSTGRESQL("PostgreSQL"),
    MYSQL("MySQL"),
    FIREBASE("Firebase"),
    DOCKER("Docker"),
    REACT("React"),
    MONGODB("MongoDB");

    final String name;
}
