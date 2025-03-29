package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.TokenRecord;
import com.slowv.udemi.service.dto.request.SignInRequest;
import com.slowv.udemi.service.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public interface AuthenticationService extends LogoutHandler {
    AccountRecord signIn(SignInRequest request, HttpServletResponse response);

    AccountRecord signUp(SignUpRequest request);

    TokenRecord getNewAccessToken(HttpServletResponse response, HttpSession session);
}
