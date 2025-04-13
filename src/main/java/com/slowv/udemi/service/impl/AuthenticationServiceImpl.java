package com.slowv.udemi.service.impl;

import com.slowv.udemi.common.utils.StringUtils;
import com.slowv.udemi.entity.AccountInfoEntity;
import com.slowv.udemi.entity.RoleEntity;
import com.slowv.udemi.entity.enums.ERole;
import com.slowv.udemi.entity.enums.TokenType;
import com.slowv.udemi.integration.mail.MailService;
import com.slowv.udemi.integration.storage.MinioService;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.repository.AccountRepository;
import com.slowv.udemi.repository.RoleRepository;
import com.slowv.udemi.security.jwt.TokenProvider;
import com.slowv.udemi.service.AuthenticationService;
import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.TokenRecord;
import com.slowv.udemi.service.dto.request.SignInRequest;
import com.slowv.udemi.service.dto.request.SignUpRequest;
import com.slowv.udemi.service.mapper.AccountMapper;
import com.slowv.udemi.web.rest.errors.BusinessException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static com.slowv.udemi.common.constant.AppConstants.KEY_SESSION_EMAIL_AUTH;
import static com.slowv.udemi.security.jwt.TokenProvider.AUTHORITIES_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final MinioService minioService;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountMapper accountMapper;

    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public AccountRecord signIn(final SignInRequest request, HttpServletResponse response) {
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        final var authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        final var token = tokenProvider.createTokenRsa(authentication);
        final var refreshToken = tokenProvider.createRefreshTokenRsa(authentication);

        creatRefreshTokenCookie(response, refreshToken.getTokenValue());
        final var roleString = (String) token.getClaim(AUTHORITIES_KEY);

        return new AccountRecord(
                request.email(),
                new TokenRecord(
                        token.getTokenValue(),
                        LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()),
                        TokenType.Bearer.name()
                ),
                new TokenRecord(
                        refreshToken.getTokenValue(),
                        LocalDateTime.ofInstant(Objects.requireNonNull(refreshToken.getExpiresAt()), ZoneId.systemDefault()),
                        TokenType.Refresh.name()
                ),
                null,
                List.of(roleString.split(","))
        );
    }

    @Override
    public AccountRecord signUp(final SignUpRequest request) {

        if (accountRepository.existsByEmail(request.email())) {
            throw new BusinessException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Email is already in used");
        }

        final var account = accountMapper.toEntity(request);

        RoleEntity role = roleRepository.findByName(ERole.STUDENT);
        account.setPassword(passwordEncoder.encode(request.password()));
        account.addRole(role);

        final var accountInfo = new AccountInfoEntity();
        accountInfo.setLastName(request.lastName());
        accountInfo.setFirstName(request.firstName());
        accountInfo.setPhone(request.phone());
        accountInfo.setIntroduce(request.introduce());

        log.info("start giờ");
        final var avatarUrl = minioService.upload(
                UploadFileAgrs.builder()
                        .path("users/avatar")
                        .multipartFile(request.avatar())
                        .build()
        );
        log.info("end giờ");

        accountInfo.setAvatarUrl(avatarUrl);
        account.setAccountInfo(accountInfo);

        log.info("start giờ");
        mailService.sendEmailActiveAccount(
                account.getEmail(),
                "Đăng ký tài khoản thành công!",
                account,
                StringUtils.generateRandomString(6)
        );
        log.info("end giờ");

        log.info("start giờ");
        accountRepository.save(account);
        log.info("end giờ");

        return accountMapper.toDto(account);
    }

    @Override
    public TokenRecord getNewAccessToken(final HttpServletResponse response, final HttpSession session) {
        final var email = (String) session.getAttribute(KEY_SESSION_EMAIL_AUTH);

        final var account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(HttpStatus.UNAUTHORIZED.value())));

        final var token = tokenProvider.createTokenRsa(tokenProvider.getAuthentication(account));

        return new TokenRecord(
                token.getTokenValue(),
                LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()),
                TokenType.Bearer.name()
        );
    }

    private void creatRefreshTokenCookie(final HttpServletResponse servletResponse, final String refreshToken) {
        final var refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 15 day to seconds
        servletResponse.addCookie(refreshTokenCookie);
    }

    @Override
    public void logout(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) {
    }
}
