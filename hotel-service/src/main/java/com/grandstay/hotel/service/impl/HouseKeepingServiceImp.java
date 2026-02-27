package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.model.HousekeepingTask;
import com.grandstay.hotel.service.HouseKeepingService;
import com.grandstay.hotel.util.wrappers.HouseKeepingResponse;
import com.grandstay.hotel.util.wrappers.HousekeepingRequest;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseKeepingServiceImp implements HouseKeepingService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public HouseKeepingResponse createTask(HousekeepingRequest task) {
        HousekeepingTask hk = new HousekeepingTask();
        if (task.getReservation() != null) {
            hk.setReservation(task.getReservation());
            if (hk.getReservation().getRoom() != null) hk.setRoom(hk.getReservation().getRoom());
        }
        if (task.getRoom() != null) hk.setRoom(task.getRoom());
        hk.setTaskType(task.getTaskType());
        hk.setStatus(task.getStatus() == null ? HousekeepingTask.TaskStatus.values()[0] : task.getStatus());
        hk.setAssignedTo(task.getAssignedTo());
        entityManager.persist(hk);
        return mapToResponse(hk);
    }

    @Override
    public List<HouseKeepingResponse> getTasks() {
        List<HousekeepingTask> tasks = entityManager.createQuery("SELECT h FROM HousekeepingTask h ORDER BY h.createdAt DESC", HousekeepingTask.class)
                .getResultList();
        return tasks.stream().map(this::mapToResponse).toList();
    }

    @Override
    public HouseKeepingResponse getTasksById(Long roomId) {
        List<HousekeepingTask> tasks = entityManager.createQuery("SELECT h FROM HousekeepingTask h WHERE h.room.roomId = :roomId ORDER BY h.createdAt DESC", HousekeepingTask.class)
                .setParameter("roomId", roomId)
                .setMaxResults(1)
                .getResultList();
        if (tasks.isEmpty()) throw new ResourceNotFoundException("No housekeeping tasks found for room: " + roomId);
        return mapToResponse(tasks.get(0));
    }

    @Override
    public HouseKeepingResponse updateTaskStatus(Long taskId) {
        HousekeepingTask hk = entityManager.find(HousekeepingTask.class, taskId);
        if (hk == null) throw new ResourceNotFoundException("Housekeeping task not found: " + taskId);
        // advance status to next enum value (e.g. PENDING -> IN_PROGRESS -> COMPLETED)
        HousekeepingTask.TaskStatus[] values = HousekeepingTask.TaskStatus.values();
        int nextOrdinal = hk.getStatus().ordinal() + 1;
        if (nextOrdinal < values.length) {
            hk.setStatus(values[nextOrdinal]);
        }
        entityManager.merge(hk);
        return mapToResponse(hk);
    }

    private HouseKeepingResponse mapToResponse(HousekeepingTask hk) {
        HouseKeepingResponse r = new HouseKeepingResponse();
        r.setTaskId(hk.getTaskId());
        r.setRoom(hk.getRoom());
        r.setReservation(hk.getReservation());
        r.setTaskType(hk.getTaskType());
        r.setStatus(hk.getStatus());
        r.setAssignedTo(hk.getAssignedTo());
        r.setCreatedAt(hk.getCreatedAt());
        r.setUpdatedAt(hk.getUpdatedAt());
        return r;
    }
}
