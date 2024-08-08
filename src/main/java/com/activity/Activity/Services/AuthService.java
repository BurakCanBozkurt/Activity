package com.activity.Activity.Services;

import com.activity.Activity.Model.JWTResponseDto;
import com.activity.Activity.Model.LoginDto;
import com.activity.Activity.Model.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    JWTResponseDto login(LoginDto loginDto);
    ResponseEntity<String> register(RegisterDto registerDto);
}
