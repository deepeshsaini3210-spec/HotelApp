package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.HousekeepingController;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.HouseKeepingService;
import com.grandstay.hotel.util.wrappers.HouseKeepingResponse;
import com.grandstay.hotel.util.wrappers.HousekeepingRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/housekeeping")
public class HouseKeepingControlerImp implements HousekeepingController {

    @PersistenceContext
    private EntityManager entityManager;

    private final HouseKeepingService houseKeepingService;

    public HouseKeepingControlerImp(HouseKeepingService houseKeepingService) {
        this.houseKeepingService = houseKeepingService;
    }

    @Override
    public HouseKeepingResponse createTask(@RequestBody HousekeepingRequest task) {
        if (task.getRoom() != null && task.getRoom().getRoomId() != null) {
            Room loaded = entityManager.find(Room.class, task.getRoom().getRoomId());
            if (loaded != null) {
                task.setRoom(loaded);
            }
        }
        return houseKeepingService.createTask(task);
    }

    @Override
    public List<HouseKeepingResponse> getTasks() {
        return houseKeepingService.getTasks();
    }

    @Override
    public HouseKeepingResponse getTasksById(@PathVariable Long roomId) {
        return houseKeepingService.getTasksById(roomId);
    }

    @Override
    public HouseKeepingResponse updateTaskStatus(@PathVariable Long taskId) {
        return houseKeepingService.updateTaskStatus(taskId);
    }
}
