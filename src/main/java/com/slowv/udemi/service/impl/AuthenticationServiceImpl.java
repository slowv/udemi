package com.slowv.udemi.service.impl;

import com.slowv.udemi.controller.errors.BusinessException;
import com.slowv.udemi.entity.AccountInfoEntity;
import com.slowv.udemi.entity.RoleEntity;
import com.slowv.udemi.entity.enums.TokenType;
import com.slowv.udemi.entity.enums.ERole;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final MinioService minioService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountMapper accountMapper;

    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public AccountRecord signIn(final SignInRequest request) {
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        final var authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        final var token = tokenProvider.createToken(authentication);

        return new AccountRecord(
                request.email(),
                new TokenRecord(
                        token,
                        null,
                        TokenType.Bearer.name()
                ),
                null,
                null
        );
    }

    @Override
    public AccountRecord signUp(final SignUpRequest request) {

        if(accountRepository.existsByEmail(request.email())) {
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

        final var avatarUrl = minioService.upload(
                UploadFileAgrs.builder()
                        .path("users/avatar")
                        .file(request.avatar())
                        .build()
        );

        accountInfo.setAvatarUrl(avatarUrl);

        account.setAccountInfo(accountInfo);

        return accountMapper.toDto(accountRepository.save(account));
    }
}
