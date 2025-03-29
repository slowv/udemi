package com.slowv.udemi.controller.impl;

import com.slowv.udemi.controller.AuthenticationController;
import com.slowv.udemi.service.AuthenticationService;
import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.request.SignInRequest;
import com.slowv.udemi.service.dto.request.SignUpRequest;
import com.slowv.udemi.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public Response<AccountRecord> signIn(final SignInRequest request) {
        return Response.ok(authenticationService.signIn(request));
    }

    @Override
    public Response<AccountRecord> signUp(final SignUpRequest request) {
        return Response.created(authenticationService.signUp(request));
    }
}
