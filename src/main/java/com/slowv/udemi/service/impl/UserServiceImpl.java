package com.slowv.udemi.service.impl;

import com.slowv.udemi.controller.errors.EmailExistException;
import com.slowv.udemi.integration.storage.MinioService;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.repository.UserRepository;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import com.slowv.udemi.service.mapper.UserMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MinioService minioService;
    private final Validator validator;
    private final UserMapper userMapper;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    @Override
    public List<UserDto> findAll() {
        log.info("Find all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUser(final Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserDto addUser(final UserDto dto) {
        final var constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (userRepository.existsByEmailOrUsername(dto.getEmail(), dto.getUsername())) {
            throw new EmailExistException("Email or Username already exist!");
        }

        final var entity = userMapper.toEntity(dto);
        final var avatarUrl = minioService.upload(
                UploadFileAgrs.builder()
                        .file(dto.getAvatar())
                        .path("/users/avatar")
                        .build()
        );
        entity.setAvatarUrl(avatarUrl);
        return userMapper.toDto(userRepository.save(entity));
    }

    @Override
    public UserDto update(final Long id, final UserDto dto) {
        final var userExist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));

        userMapper.update(dto, userExist);

        return userMapper.toDto(userRepository.save(userExist));
    }

    @Override
    public void delete(final Long id) {
        final var userExist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));
        userRepository.delete(userExist);
    }
}
