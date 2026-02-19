package com.grandstay.hotel.service;

import com.grandstay.hotel.util.wrappers.HouseKeepingResponse;
import com.grandstay.hotel.util.wrappers.HousekeepingRequest;
import com.grandstay.hotel.util.wrappers.reservationResponse;

import java.util.List;

public interface HouseKeepingService {
    public HouseKeepingResponse createTask(HousekeepingRequest task);
    public List<HouseKeepingResponse> getTasks();
    public HouseKeepingResponse getTasksById(Long roomId);
    public HouseKeepingResponse updateTaskStatus(Long taskId);

}
