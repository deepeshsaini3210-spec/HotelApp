package com.grandstay.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NamedQuery(
        name = "findById",
        query = """
        SELECT r FROM Reservation r
        JOIN FETCH r.user
        JOIN FETCH r.room
        WHERE r.reservationId = :reservationId
    """
)

@NamedQuery(
        name = "findToday",
        query = """
        SELECT r FROM Reservation r
        WHERE r.checkInDate = :CURRENT_DATE
    """
)
@NamedQuery(
        name = "getCustomerBookingByIdUser",
        query = """
        SELECT r.user FROM Reservation r WHERE r.reservationId = :reservationId AND r.status = :status AND r.checkInDate = :checkInDate
    """
)
@NamedQuery(
        name = "findUsersWithConfirmedReservationsToday",
        query = """
        SELECT DISTINCT r.user FROM Reservation r WHERE r.status = 'CONFIRMED' AND r.checkInDate = CURRENT_DATE
    """
)
@NamedQuery(
        name = "findByUserId",
        query = """
        SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.room WHERE r.user.userId = :userId
    """
)
@NamedQuery(
        name = "findAllReservations",
        query = "SELECT r FROM Reservation r")
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    public enum ReservationStatus {
        CONFIRMED,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "file_url")
    @NotNull
    private String fileUrl;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // OneToOne with Billing
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Billing billing;

    // OneToMany with HousekeepingTask
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HousekeepingTask> housekeepingTasks = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
