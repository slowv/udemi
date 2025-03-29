package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.request.SignInRequest;
import com.slowv.udemi.service.dto.request.SignUpRequest;

public interface AuthenticationService {
    AccountRecord signIn(SignInRequest request);

    AccountRecord signUp(SignUpRequest request);
}
