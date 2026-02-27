package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.service.AuthService;
import com.grandstay.hotel.service.CustomerService;
import com.grandstay.hotel.util.wrappers.UserResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthService authService;

    @Override
    public UserResponse getCustomerById(Long id) {
        User user = authService.findByIdOrThrow(id);
        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getCustomerBooking() {
        return getCustomerHistory();
    }

    

    @Override
    public List<UserResponse> getCustomerHistory() {

        List<User> users = entityManager
                .createNamedQuery("findUsersWithConfirmedReservationsToday", User.class)
                .getResultList();
//        List<User> users = authRepository.findUsersWithConfirmedReservationsToday();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : users) {
            UserResponse userResponse = mapToUserResponse(user);
            responseList.add(userResponse);
        }

        return responseList;
    }

    @Override
    public UserResponse getCustomerBookingById(Long id, Reservation.ReservationStatus status, LocalDate checkInDate) {

        User user = entityManager.createNamedQuery("getCustomerBookingByIdUser", User.class)
                .setParameter("reservationId",id)
                .setParameter("status",status)
                .setParameter("checkInDate",checkInDate)
                .getSingleResult();

        return mapToUserResponse(user);
         // return authRepository.getCustomerBookingByIds(id,status,checkInDate)
        // .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setAddress(user.getAddress());
        userResponse.setRole(user.getRole().name());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

}
