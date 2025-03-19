package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto getUser(Long id);

    UserDto addUser(UserDto dto);

    UserDto update(final Long id, UserDto dto);

    void delete(Long id);
}
