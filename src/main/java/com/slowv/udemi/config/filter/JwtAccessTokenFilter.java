package com.slowv.udemi.config.filter;

import com.slowv.udemi.security.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAccessTokenFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        var jwt = resolveToken(request);
        if (StringUtils.hasText(jwt) && tokenProvider.validateTokenRsa(jwt)) {

            // Nếu token hết hạn
            if (!tokenProvider.isTokenIsExpired(jwt)) {
                final var refreshToken = getRefreshToken(request);
                if (!ObjectUtils.isEmpty(refreshToken) && tokenProvider.validateTokenRsa(refreshToken) && tokenProvider.isTokenIsExpired(refreshToken)) {
                    jwt = tokenProvider.createTokenRsa(tokenProvider.getAuthenticationRSA(jwt)).getTokenValue();
                    creatNewAccessTokenCookie(response, jwt);
                } else {
                    jwt  = null;
                }
            }

            if (!ObjectUtils.isEmpty(jwt)) {
                final var authentication = tokenProvider.getAuthenticationRSA(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getRefreshToken(HttpServletRequest request) {
        final var cookies = request.getCookies();

        if (cookies != null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void creatNewAccessTokenCookie(final HttpServletResponse servletResponse, final String token) {
        final var accessToken = new Cookie("new_access_token", token);
        accessToken.setHttpOnly(true);
        accessToken.setSecure(true);
        accessToken.setPath("/");
        accessToken.setMaxAge(15 * 60); // 15 minute to seconds
        servletResponse.addCookie(accessToken);
    }
}
