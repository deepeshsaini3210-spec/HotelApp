package com.grandstay.hotel.controller.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandstay.hotel.controller.AuthController;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.repository.AuthRepository;
import com.grandstay.hotel.service.AuthService;
import com.grandstay.hotel.util.JwtUtil;
import com.grandstay.hotel.util.wrappers.UserLoginRequest;
import com.grandstay.hotel.util.wrappers.UserRegisterRequest;
import com.grandstay.hotel.util.wrappers.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthControllerControllerImp implements AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

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

    @Override
    @GetMapping("/users/housekeeping")
    public List<UserResponse> getHousekeepingUsers() {
        List<User> users = authRepository.findByRoleOrderByNameAsc(User.Roles.HOUSEKEEPING);
        return users.stream().map(this::toUserResponse).collect(Collectors.toList());
    }

    private UserResponse toUserResponse(User u) {
        UserResponse r = new UserResponse();
        r.setUserId(u.getUserId());
        r.setName(u.getName());
        r.setEmail(u.getEmail());
        r.setRole(u.getRole() != null ? u.getRole().name() : null);
        r.setPhone(u.getPhone());
        r.setAddress(u.getAddress());
        r.setCreatedAt(u.getCreatedAt());
        r.setUpdatedAt(u.getUpdatedAt());
        return r;
    }
}
