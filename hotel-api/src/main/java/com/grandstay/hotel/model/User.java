package com.grandstay.hotel.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@NamedQueries({
    @NamedQuery(name = "findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "findUserById", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "findAllUsers", query = "SELECT u FROM User u")
})
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    public enum Roles {
        CUSTOMER,
        FRONT_DESK,
        HOUSEKEEPING,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;


    private String phone;
    @JsonIgnore
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
