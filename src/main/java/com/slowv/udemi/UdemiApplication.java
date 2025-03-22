package com.slowv.udemi;

import com.slowv.udemi.config.properties.MinioProperties;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableConfigurationProperties({MinioProperties.class})
public class UdemiApplication {

    private static final Logger log = LoggerFactory.getLogger(UdemiApplication.class);

    public static void main(String[] args) {
        final var app = new SpringApplication(UdemiApplication.class);
        final var env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(final Environment env) {
        var protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        final var serverPort = env.getProperty("server.port");
        var contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        log.info(
                """
                ----------------------------------------------------------
                Application '{}' is running! Access URLs:
                Local     : {}://localhost:{}{}
                External  : {}://{}:{}{}
                Profile(s): {}
                ----------------------------------------------------------
                """,
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }
}
