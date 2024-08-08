package com.activity.Activity.Services;

import com.activity.Activity.GlobalExceptionHandler.EmailOrPasswordException;
import com.activity.Activity.GlobalExceptionHandler.StudentAlreadyExistsException;
import com.activity.Activity.Model.*;
import com.activity.Activity.Repository.RoleRepository;
import com.activity.Activity.Repository.UserRepository;
import com.activity.Activity.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JWTResponseDto login(LoginDto loginDto) {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        JWTResponseDto jwtResponseDto=new JWTResponseDto();
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            String token = jwtTokenProvider.generateToken(authentication);
            jwtResponseDto.setToken(token);
        }else{
            throw new EmailOrPasswordException("Email Or Password is wrong");
        }

        return jwtResponseDto;
    }
    @Override
    public ResponseEntity<String> register(RegisterDto registerDto) {
        User newUser = new User();
        newUser.setName(registerDto.getName());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setEmail(registerDto.getEmail());
        newUser.setSurname(registerDto.getSurname());
        Set<Role> roleSet = new HashSet<>();
        Role role;

        if (registerDto.isUserType()) {
            role = roleRepository.findByName("TEACHER")
                    .orElseThrow();

        } else {
            role = roleRepository.findByName("STUDENT")
                    .orElseThrow();
        }

        roleSet.add(role);
        newUser.setRoles(roleSet);
        User existingUser = userRepository.findByEmail(newUser.getEmail());

        if (existingUser != null) {
            throw new StudentAlreadyExistsException("A user with this email already exists.");
        } else {
            User registeredUser = userRepository.save(newUser);
            User.QrGenerate(newUser);
            return ResponseEntity.ok("User registered successfully.");
        }
    }
}
