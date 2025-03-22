package com.slowv.udemi.service.impl;

import com.slowv.udemi.controller.errors.EmailExistException;
import com.slowv.udemi.controller.errors.FieldValidationException;
import com.slowv.udemi.entity.UserEntity;
import com.slowv.udemi.integration.storage.MinioService;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.repository.UserRepository;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MinioService minioService;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    @Override
    public List<UserDto> findAll() {
        log.info("Find all users");
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public UserDto getUser(final Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromUser)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserDto addUser(final UserDto dto) {
        validateUser(dto);

        if (userRepository.existsByEmailOrUsername(dto.getEmail(), dto.getUsername())) {
            throw new EmailExistException("Email or Username already exist!");
        }

        final var entity = dto.toEntity();
        final var avatarUrl = minioService.upload(
                UploadFileAgrs.builder()
                        .file(dto.getAvatar())
                        .path("/users/avatar")
                        .build()
        );
        entity.setAvatarUrl(avatarUrl);
        return UserDto.fromUser(userRepository.save(entity));
    }

    private void validateUser(final UserDto dto) {
        final var errors = new HashMap<String, Object>();

        // Validate field
        if (ObjectUtils.isEmpty(dto.getUsername())) {
            errors.put("username", "Username is required!");
        }

        if (ObjectUtils.isEmpty(dto.getEmail())) {
            errors.put("email", "Email is required!");
        }

        if (ObjectUtils.isEmpty(dto.getAvatar()) || dto.getAvatar().getSize() == 0) {
            errors.put("avatar", "Avatar is required!");
        }

        if (!errors.isEmpty()) {
            throw new FieldValidationException(HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        }
    }

    @Override
    public UserDto update(final Long id, final UserDto dto) {
        final var userExist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));
        userExist.setId(dto.getId());
        userExist.setEmail(dto.getEmail());
        userExist.setFirstName(dto.getFirstName());
        userExist.setLastName(dto.getLastName());
        userExist.setPhone(dto.getPhone());
        userExist.setAddress(dto.getAddress());
        return UserDto.fromUser(userRepository.save(userExist));
    }

    @Override
    public void delete(final Long id) {
        final var userExist = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE));
        userRepository.delete(userExist);
    }
}
