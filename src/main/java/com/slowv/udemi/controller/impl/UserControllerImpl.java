package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.UserController;
import com.slowv.udemi.service.UserService;
import com.slowv.udemi.service.dto.UserDto;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public Response<List<UserDto>> users(final String device, final int pageSize, final int pageNumber, final List<String> names, final String sort, final HttpServletRequest request, final HttpServletResponse response, final HttpSession session) {
        log.info("X_DEVICE {}", device);
        log.info("PAGE_SIZE {}", pageSize);
        log.info("PAGE_NUMBER {}", pageNumber);
        log.info("NAMES: {}", names);
        log.info("SORT {}", sort);
        log.info("SESSION {}", session.getId());
        log.info("REQUEST {}", request.getRequestURI());
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
