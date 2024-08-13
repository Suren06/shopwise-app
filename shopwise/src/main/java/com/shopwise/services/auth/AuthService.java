package com.shopwise.services.auth;

import com.shopwise.dto.SignUpRequest;
import com.shopwise.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignUpRequest signUpRequest);

    Boolean hasUserWithEmail(String email);
}
