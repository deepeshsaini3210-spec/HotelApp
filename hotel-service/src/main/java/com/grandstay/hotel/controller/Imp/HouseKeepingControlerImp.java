package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.HousekeepingController;
import com.grandstay.hotel.util.wrappers.HouseKeepingResponse;
import com.grandstay.hotel.util.wrappers.HousekeepingRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/housekeeping")
public class HouseKeepingControlerImp implements HousekeepingController {
    @Override
    public HouseKeepingResponse createTask(HousekeepingRequest task) {
        return null;
    }

    @Override
    public List<HouseKeepingResponse> getTasks() {
        return List.of();
    }

    @Override
    public HouseKeepingResponse getTasksById(Long roomId) {
        return null;
    }

    @Override
    public HouseKeepingResponse updateTaskStatus(Long taskId) {
        return null;
    }
}
