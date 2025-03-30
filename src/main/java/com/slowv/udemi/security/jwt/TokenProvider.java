package com.slowv.udemi.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.slowv.udemi.config.properties.SecurityProperties;
import com.slowv.udemi.controller.errors.BusinessException;
import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.entity.RoleEntity;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    public static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

    private final JwtParser jwtParser;
    private final Key key;
    private final long tokenValidityInMilliseconds;
    private final JwtEncoder jwtEncoder;
    private final Long expireMinute;
    private final JwtDecoder jwtDecoder;

    public TokenProvider(final SecurityProperties securityProperties, final JwtEncoder jwtEncoder, final JwtDecoder jwtDecoder) {
        byte[] keyBytes;
        var secret = securityProperties.getJwtSecret();
        keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

        this.tokenValidityInMilliseconds = 1000 * securityProperties.getJwtExpiration();
        this.jwtEncoder = jwtEncoder;
        this.expireMinute = securityProperties.getRsa().getExpire();
        this.jwtDecoder = jwtDecoder;
    }

//    public String createToken(Authentication authentication) {
//        String authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        return Jwts
//                .builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .signWith(key, SignatureAlgorithm.HS512)
//                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
//                .compact();
//    }

    public Jwt createTokenRsa(final Authentication authentication) {
        log.info("[TokenProvider:createToken] Token Creation Started for:{}", authentication.getName());

        final var authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        final var now = Instant.now();

        final var claims = JwtClaimsSet.builder()
                .issuer("udemiIssuer")
                .issuedAt(now)
                .expiresAt(now.plus(expireMinute, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

//    public String createRefreshToken(Authentication authentication) {
//        log.info("[TokenProvider:createRefreshToken] Token Creation Started for:{}", authentication.getName());
//
//        return Jwts
//                .builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, "REFRESH_TOKEN")
//                .signWith(key, SignatureAlgorithm.HS512)
//                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
//                .compact();
//    }

    public Jwt createRefreshTokenRsa(Authentication authentication) {
        log.info("[TokenProvider:createRefreshToken] Token Creation Started for:{}", authentication.getName());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("udemiIssuer")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, "REFRESH_TOKEN")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

//    public boolean validateToken(String token) {
//        try {
//            jwtParser.parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            log.trace(INVALID_JWT_TOKEN, e);
//        } catch (UnsupportedJwtException e) {
//            log.trace(INVALID_JWT_TOKEN, e);
//        } catch (MalformedJwtException e) {
//            log.trace(INVALID_JWT_TOKEN, e);
//        } catch (SignatureException e) {
//            log.trace(INVALID_JWT_TOKEN, e);
//        } catch (IllegalArgumentException e) {
//            log.error("Token validation error {}", e.getMessage());
//        }
//        return false;
//    }

    public boolean validateTokenRsa(String token) {
        try {
            JWTParser.parse(token);
            return true;
        } catch (ParseException e) {
            log.error(INVALID_JWT_TOKEN, e);
            return false;
        }
    }

    public boolean isTokenIsExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isAfter(Instant.now());
    }

    public boolean isTokenIsExpired(String token) {
       try {
           final var jwt = jwtDecoder.decode(token);
           return isTokenIsExpired(jwt);
       } catch (Exception ignored) {
           return false;
       }
    }

//    public Authentication getAuthentication(final String jwt) {
//        final var claims = jwtParser.parseClaimsJws(jwt).getBody();
//
//        Collection<? extends GrantedAuthority> authorities = Arrays
//                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                .filter(auth -> !auth.trim().isEmpty())
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//
//        User principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
//    }

    public Authentication getAuthenticationRSA(final String token) {
        try {
            final var claims = JWTParser.parse(token).getJWTClaimsSet();
            Collection<? extends GrantedAuthority> authorities = Arrays
                    .stream(claims.getClaim(AUTHORITIES_KEY).toString().split(","))
                    .filter(auth -> !auth.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            final var principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch (ParseException e) {
            log.error(INVALID_JWT_TOKEN, e);
            throw new BusinessException(INVALID_JWT_TOKEN);
        }
    }


    public Authentication getAuthentication(final AccountEntity account) {
        // Extract user details from UserDetailsEntity
        String username = account.getEmail();
        String password = account.getPassword();
        final var authorities = account.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .map(Enum::name)
                .map("ROLE_"::concat)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
}
