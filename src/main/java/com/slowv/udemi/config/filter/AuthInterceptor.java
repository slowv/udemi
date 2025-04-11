package com.slowv.udemi.config.filter;

import com.slowv.udemi.security.jwt.TokenProvider;
import com.slowv.udemi.web.rest.errors.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                // Xác thực token ở đây nếu muốn
                if (tokenProvider.validateTokenRsa(token) && tokenProvider.isTokenIsExpired(token)) {
                    final var authentication = tokenProvider.getAuthenticationRSA(token);
                    accessor.setUser(authentication);
                }
            }
        }

        if (ObjectUtils.isEmpty(accessor.getUser())) {
            throw new MessageDeliveryException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return message;
    }
}
