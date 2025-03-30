package com.slowv.udemi.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(
        prefix = "jwt",
        ignoreUnknownFields = false
)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityProperties {
    final Rsa rsa = new Rsa();

    String jwtSecret = UdemiDefault.Jwt.jwtSecret;
    long jwtExpiration = UdemiDefault.Jwt.jwtExpiration;
    long rememberMeExpiration = UdemiDefault.Jwt.rememberMeExpiration;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Rsa {
        RSAPublicKey publicKey;
        RSAPrivateKey privateKey;
        Long expire;
    }
}
