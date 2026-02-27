package com.grandstay.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NamedQueries({
    @NamedQuery(name = "findByReservationId", query = "SELECT b FROM Billing b WHERE b.reservation.reservationId = :reservationId"),
    @NamedQuery(name = "findBillingById", query = "SELECT b FROM Billing b WHERE b.billingId = :billingId"),
    @NamedQuery(name = "findAllBillings", query = "SELECT b FROM Billing b"),
    @NamedQuery(name = "findPaidBillings", query = "SELECT b FROM Billing b WHERE b.paymentStatus = 'PAID'"),
    @NamedQuery(name = "findUnpaidBillings", query = "SELECT b FROM Billing b WHERE b.paymentStatus = 'PENDING'")
})
@Entity
@Table(name = "BILLING")
@Data
public class Billing {

    public enum PaymentStatus {
        PENDING,
        PAID
    }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long billingId;

        @OneToOne
        @JoinColumn(name = "reservation_id")
        private Reservation reservation;

        @Column(name = "ROOMCHARGES" )
        private BigDecimal roomCharges;
        @Column(name = "EXTRACHARGES")
        private BigDecimal extraCharges;
        @Column(name = "DISCOUNT")
        private BigDecimal discount;
        @Column(name = "TOTALAMOUNT")
        private BigDecimal totalAmount;

        @Column(name = "PAYMENTSTATUS")
        @Enumerated(EnumType.STRING)
        private PaymentStatus paymentStatus;

        @Column(name = "CREATEDAT",updatable = false)
        @CreationTimestamp
        private LocalDateTime createdAt;

        @Column(name = "UPDATEDAT", updatable = true)
        @UpdateTimestamp
        private LocalDateTime updatedAt;
    }


