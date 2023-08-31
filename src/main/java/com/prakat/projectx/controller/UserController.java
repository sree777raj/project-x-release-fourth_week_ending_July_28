package com.prakat.projectx.controller;

import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.OtpRequestDTO;
import com.prakat.projectx.dto.OtpResponseDTO;
import com.prakat.projectx.dto.ResetPasswordDto;
import com.prakat.projectx.dto.UserDto;
import com.prakat.projectx.entity.User;
import com.prakat.projectx.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class UserController {
    private static final Logger logger= LoggerFactory.getLogger(UserController.class);
       @Autowired

    public UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<String> sayHello() {


        return ResponseEntity.ok("Hello from secure endpoint");
    }

    @PostMapping("/create_users")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userServiceImpl.createUser(userDto);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userServiceImpl.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping("/otp")
    public ResponseEntity<OtpResponseDTO> postForgotPassword(@RequestBody OtpRequestDTO request) throws Exception {
        String result = userServiceImpl.forgotPassword(request.getEmail());
        OtpResponseDTO response = new OtpResponseDTO();
        response.setOtpsealedobject(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resetPassword")
    public AuthenticationResponse getResetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws Exception {
        return userServiceImpl.resetPassword(resetPasswordDto);
    }

}