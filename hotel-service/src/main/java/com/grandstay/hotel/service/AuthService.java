package com.grandstay.hotel.service;

import com.grandstay.hotel.generic.IBaseService;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.util.wrappers.UserLoginRequest;
import com.grandstay.hotel.util.wrappers.UserRegisterRequest;
import com.grandstay.hotel.util.wrappers.UserResponse;

import java.util.Optional;

public interface AuthService extends IBaseService<User, Long> {

    Optional<User> findUserByEmail(String email);

    UserResponse userlogin(UserLoginRequest user);

    UserResponse userRegister(UserRegisterRequest user);
}
