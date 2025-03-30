package com.slowv.udemi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "cache",
        ignoreUnknownFields = false
)
public class CacheProperties {
    private final Redis redis = new Redis();

    @Data
    public static class Redis {
        private String[] server;
        private long expiration;
        private boolean cluster;
        private int connectionPoolSize;
        private int connectionMinimumIdleSize;
        private int subscriptionConnectionPoolSize;
        private int subscriptionConnectionMinimumIdleSize;

        public Redis() {
            this.server = UdemiDefault.Redis.server;
            this.expiration = UdemiDefault.Redis.expiration;
            this.cluster = UdemiDefault.Redis.cluster;
            this.connectionPoolSize = UdemiDefault.Redis.connectionPoolSize;
            this.connectionMinimumIdleSize = UdemiDefault.Redis.connectionMinimumIdleSize;
            this.subscriptionConnectionPoolSize = UdemiDefault.Redis.subscriptionConnectionPoolSize;
            this.subscriptionConnectionMinimumIdleSize = UdemiDefault.Redis.subscriptionConnectionMinimumIdleSize;
        }
    }
}