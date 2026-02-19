package com.grandstay.hotel.controller.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandstay.hotel.controller.AuthController;
import com.grandstay.hotel.service.AuthService;
import com.grandstay.hotel.util.JwtUtil;
import com.grandstay.hotel.util.wrappers.UserLoginRequest;
import com.grandstay.hotel.util.wrappers.UserRegisterRequest;
import com.grandstay.hotel.util.wrappers.UserResponse;

@RestController
@RequestMapping("/auth")
public class authControllerControllerImp implements AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponse createUserAndUpdate(UserRegisterRequest user) {
        return authService.userRegister(user);
    }

    @Override
    public UserResponse userLogin(UserLoginRequest user) {
        UserResponse response = authService.userlogin(user);
        String token = jwtUtil.generateToken(response.getEmail(), response.getRole());
        response.setToken(token);
        return response;
    }
}
