package com.slowv.udemi.controller;

import com.slowv.udemi.service.dto.UserDto;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    Response<List<UserDto>> users(
            @RequestHeader("X_DEVICE") final String device,
            @RequestParam("page-size") int pageSize,
            @RequestParam("page-number") int pageNumber,
            @RequestParam("names") List<String> names,
            @RequestParam(value = "sort", required = false) String sort,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    );

    @GetMapping("/{id}")
    Response<UserDto> getUser(@PathVariable final Long id);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<UserDto> addUser(
            @ModelAttribute final UserDto userDto
    );

    @PutMapping("/{id}")
    Response<UserDto> updateUser(@RequestBody final UserDto userDto, @PathVariable final Long id);

    @DeleteMapping("/{id}")
    Response<Void> deleteUser(@PathVariable final Long id);
}
