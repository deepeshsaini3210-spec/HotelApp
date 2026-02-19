package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.model.User;
import com.grandstay.hotel.service.AuthService;
import com.grandstay.hotel.util.wrappers.UserResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CustomerServiceImpTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private AuthService authService;

    @Mock
    private TypedQuery<User> typedQuery;

    @InjectMocks
    private CustomerServiceImp customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerHistory() {
        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setRole(User.Roles.CUSTOMER);

        when(entityManager.createNamedQuery(eq("findUsersWithConfirmedReservationsToday"), eq(User.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(user));

        List<UserResponse> result = customerService.getCustomerHistory();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(entityManager, times(1)).createNamedQuery(eq("findUsersWithConfirmedReservationsToday"), eq(User.class));
    }
}
