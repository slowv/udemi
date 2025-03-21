package com.slowv.udemi.controller;

import com.slowv.udemi.service.dto.UserDto;
import com.slowv.udemi.service.dto.response.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    Response<List<UserDto>> users();

    @GetMapping("/{id}")
    Response<UserDto> getUser(@PathVariable final Long id);

    @PostMapping
    Response<UserDto> addUser(@RequestBody final UserDto userDto);

    @PutMapping("/{id}")
    Response<UserDto> updateUser(@RequestBody final UserDto userDto, @PathVariable final Long id);

    @DeleteMapping("/{id}")
    Response<Void> deleteUser(@PathVariable final Long id);
}
