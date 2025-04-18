package com.slowv.udemi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
//                .simpDestMatchers("/chat/**").hasAnyAuthority("ROLE_TEACHER")
                .anyMessage()
                .permitAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
