package com.slowv.udemi.controller;

import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.TokenRecord;
import com.slowv.udemi.service.dto.request.SignInRequest;
import com.slowv.udemi.service.dto.request.SignUpRequest;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface AuthenticationController {

    @PostMapping("/sign-in")
    Response<AccountRecord> signIn(@Valid @RequestBody SignInRequest request, HttpServletResponse response);

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<AccountRecord> signUp(@Valid @ModelAttribute SignUpRequest request);

    @Secured("REFRESH_TOKEN")
    @PostMapping("/refresh-token")
    Response<TokenRecord> refreshToken(HttpServletResponse response, HttpSession session);
}
