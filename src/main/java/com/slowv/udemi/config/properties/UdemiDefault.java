package com.slowv.udemi.config.properties;

public interface UdemiDefault {

    interface Jwt {
        String jwtSecret = "MWM0NzE4MDk2MGQwMWFkNjZiNDQ5ZTJkMTJjYTE2N2M1YTFhY2E3M2UzMzBlMGMzZjU3OGVhMGQwMmQyZGM5OTgyODJlOWIwZGVhMzJkOTUzNTdlMjM4ZDIxMTk0YjgzNmEzNDNlMTBjZTMwMGMyNjgzYTc2ZTlmZjE5MzZkZmM=";
        long jwtExpiration = 86400;
        long rememberMeExpiration = 2592000;
    }

}
