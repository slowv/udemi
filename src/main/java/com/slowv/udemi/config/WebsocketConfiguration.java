package com.slowv.udemi.config;

import com.slowv.udemi.config.filter.AuthInterceptor;
import com.slowv.udemi.config.filter.HttpSessionHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(httpSessionHandshakeInterceptor)
                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authInterceptor);
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat", "/lobby", "/notifications");
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                Principal principal = request.getPrincipal();
//                if (principal == null) {
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    authorities.add(new SimpleGrantedAuthority("ANONYMOUS"));
//                    principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
//                }
                return principal;
            }
        };
    }
}
