package com.activity.Activity.RestController;

import com.activity.Activity.Model.JWTResponseDto;
import com.activity.Activity.Model.LoginDto;
import com.activity.Activity.Model.RegisterDto;
import com.activity.Activity.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody  RegisterDto registerDto){
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponseDto> login(@RequestBody LoginDto loginDto){
        JWTResponseDto result= authService.login(loginDto);
        return ResponseEntity.ok(result);
    }
}
