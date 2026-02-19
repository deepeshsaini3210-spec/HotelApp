package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.grandstay.hotel.util.wrappers.HouseKeepingResponse;
import com.grandstay.hotel.util.wrappers.HousekeepingRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(url = "${hotel-app-service.url}",name = "housekeepingController",path = "housekeeping")
public interface HousekeepingController {

    // POST /housekeeping/tasks
    // GET  /housekeeping/tasks
    // GET  /housekeeping/tasks/room/{roomId}
    // PUT  /housekeeping/tasks/{taskId}/status

    @PostMapping("/housekeeping/tasks")
    public HouseKeepingResponse createTask(@RequestBody HousekeepingRequest task);
    
    @GetMapping("/housekeeping/tasks")
    public List<HouseKeepingResponse> getTasks();

    @GetMapping("/housekeeping/tasks/room/{roomId}")
    public HouseKeepingResponse getTasksById(@PathVariable Long roomId);

    @PutMapping("/housekeeping/tasks/{taskId}/status")
    public HouseKeepingResponse updateTaskStatus(@PathVariable Long taskId);

}
