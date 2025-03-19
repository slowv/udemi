package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.UserController;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public List<UserDto> users() {
        return userService.findAll();
    }

    @Override
    public UserDto getUser(final Long id) {
        return userService.getUser(id);
    }

    @Override
    public UserDto addUser(final UserDto dto) {
        return userService.addUser(dto);
    }

    @Override
    public UserDto updateUser(final UserDto dto, @PathVariable final Long id) {
        return userService.update(id, dto);
    }

    @Override
    public void deleteUser(final Long id) {
        userService.delete(id);
    }
}
