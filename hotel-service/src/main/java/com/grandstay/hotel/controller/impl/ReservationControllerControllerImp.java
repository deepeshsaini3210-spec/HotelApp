package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.ReservationController;
import com.grandstay.hotel.integration.Service.StorageMiniOService;
import com.grandstay.hotel.service.ReservationService;
import com.grandstay.hotel.util.wrappers.ReservationRequest;
import com.grandstay.hotel.util.wrappers.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationControllerControllerImp implements ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StorageMiniOService storageMiniOService;

    @Override
    @PostMapping("/create/booking")
    public ReservationResponse createCustomerAndUpdate(
            @RequestPart("file") MultipartFile file,
            @RequestPart("reservation") ReservationRequest reservation) {
        return reservationService.createCustomerAndUpdate(file, reservation);
    }

    @Override
    public ReservationResponse getReservationById(Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @Override
    public List<ReservationResponse> getReservationByCustomerId(Long customerId) {
        return reservationService.getReservationByCustomerId(customerId);
    }

    @Override
    public Boolean reservationCancel(Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    /** Download the file uploaded with this reservation (e.g. ID proof). */
    @GetMapping("/{reservationId}/file")
    public void downloadReservationFile(HttpServletResponse response,
                                        @org.springframework.web.bind.annotation.PathVariable Long reservationId) throws Exception {
        ReservationResponse res = reservationService.getReservationById(reservationId);
        String fileUrl = res.getFileUrl();
        if (fileUrl == null || fileUrl.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No file for this reservation");
            return;
        }
        String objectName = fileUrl.contains("/") ? fileUrl.substring(fileUrl.lastIndexOf('/') + 1) : fileUrl;
        String downloadName = objectName.contains("_") ? objectName.substring(objectName.indexOf('_') + 1) : objectName;
        try (InputStream in = storageMiniOService.downloadFile(objectName)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(downloadName, StandardCharsets.UTF_8) + "\"");
            try (OutputStream out = response.getOutputStream()) {
                in.transferTo(out);
            }
        }
    }
}

