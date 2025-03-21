package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.UserController;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import com.slowv.udemi.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public Response<List<UserDto>> users() {
        return Response.ok(userService.findAll());
    }

    @Override
    public Response<UserDto> getUser(final Long id) {
        return Response.ok(userService.getUser(id));
    }

    @Override
    public Response<UserDto> addUser(final UserDto dto) {
        return Response.created(userService.addUser(dto));
    }

    @Override
    public Response<UserDto> updateUser(final UserDto dto, @PathVariable final Long id) {
        return Response.ok(userService.update(id, dto));
    }

    @Override
    public Response<Void> deleteUser(final Long id) {
        userService.delete(id);
        return Response.noContent();
    }
}
