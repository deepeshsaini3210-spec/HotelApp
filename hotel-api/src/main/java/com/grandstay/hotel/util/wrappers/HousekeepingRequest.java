package com.grandstay.hotel.util.wrappers;

import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.model.HousekeepingTask.TaskStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HousekeepingRequest {
    private Room room;
    private Reservation reservation;
    private String taskType;
    private TaskStatus status;
    private String assignedTo;
}
