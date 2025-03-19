package com.slowv.udemi.service.impl;

import com.slowv.udemi.repository.UserRepository;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        return UserDto.fromUser(userRepository.save(dto.toEntity()));
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
