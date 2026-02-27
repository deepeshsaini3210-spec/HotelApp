package com.grandstay.hotel.service.impl;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grandstay.hotel.exceptions.ConflictException;
import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.exceptions.UnauthorizedException;
import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.service.AuthService;

import com.grandstay.hotel.util.wrappers.UserLoginRequest;
import com.grandstay.hotel.util.wrappers.UserRegisterRequest;
import com.grandstay.hotel.util.wrappers.UserResponse;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthServiceImp extends BaseServiceImp<User, Long> implements AuthService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthServiceImp( EntityManager entityManager) {
        super(entityManager, User.class, User::getUserId);

    }

    // @Override
    // public Optional<User> findById(Long id) {
    //     List<User> list = entityManager.createNamedQuery("findUserById", User.class)
    //             .setParameter("userId", id)
    //             .getResultList();
    //     return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    // }

    // @Override
    // public List<User> findAll() {
    //     return entityManager.createNamedQuery("findAllUsers", User.class).getResultList();
    // }

    @Override
    public Optional<User> findUserByEmail(String email) {
        List<User> list = entityManager.createNamedQuery("findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public UserResponse userlogin(UserLoginRequest request) {
        List<User> list = entityManager.createNamedQuery("findByEmail", User.class)
                .setParameter("email", request.getEmail())
                .getResultList();
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + request.getEmail());
        }
        User user = list.get(0);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse userRegister(UserRegisterRequest request) {
        List<User> existing = entityManager.createNamedQuery("findByEmail", User.class)
                .setParameter("email", request.getEmail())
                .getResultList();
        if (!existing.isEmpty()) {
            throw new ConflictException("Email already registered");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .address(request.getAddress())
                .role(User.Roles.valueOf(request.getRole().toUpperCase()))
                .build();
        save(user);

        return mapToUserResponse(user);
    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
