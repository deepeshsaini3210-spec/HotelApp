package com.grandstay.hotel.util.wrappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.grandstay.hotel.model.Reservation.ReservationStatus;

import com.grandstay.hotel.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long reservationId;
    private Long customer;
    private Long roomId;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private String fileUrl;
    private Long billingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}
