package com.slowv.udemi.config.filter;

import com.slowv.udemi.security.SecurityUtils;
import com.slowv.udemi.security.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.slowv.udemi.common.constant.AppConstants.KEY_HEADER_X_REFRESH_TOKEN;
import static com.slowv.udemi.common.constant.AppConstants.KEY_SESSION_EMAIL_AUTH;

@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        final var refreshToken = request.getHeader(KEY_HEADER_X_REFRESH_TOKEN);

        if (StringUtils.hasText(refreshToken) && tokenProvider.validateToken(refreshToken)) {
            final var authentication = tokenProvider.getAuthentication(refreshToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute(KEY_SESSION_EMAIL_AUTH, SecurityUtils.getCurrentUserLogin().orElse("SYSTEM"));
        } else {
            SecurityUtils.responseFailCredential(response, HttpStatus.UNAUTHORIZED, "Refresh token is invalid");
        }
        filterChain.doFilter(request, response);
    }
}
