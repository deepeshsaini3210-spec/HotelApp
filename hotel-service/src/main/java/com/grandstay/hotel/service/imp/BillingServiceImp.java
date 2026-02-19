package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Billing;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.service.BillingService;
import com.grandstay.hotel.util.wrappers.BillingResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillingServiceImp extends BaseServiceImp<Billing, Long> implements BillingService {



    public BillingServiceImp(EntityManager entityManager) {
        super(entityManager, Billing.class, Billing::getBillingId);
    }

    @Override
    public Optional<Billing> findById(Long id) {
        List<Billing> list = entityManager.createNamedQuery("findBillingById", Billing.class)
                .setParameter("billingId", id)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Billing> findAll() {
        return entityManager.createNamedQuery("findAllBillings", Billing.class).getResultList();
    }

    @Override
    public BillingResponse generateBilling(Long reservationId) {
        List<Reservation> list = entityManager.createNamedQuery("findById", Reservation.class)
                .setParameter("reservationId", reservationId)
                .getResultList();
        if (list.isEmpty()) throw new RuntimeException("Reservation not found: " + reservationId);
        Reservation reservation = list.get(0);

        java.time.LocalDate in = reservation.getCheckInDate();
        java.time.LocalDate out = reservation.getCheckOutDate();
        long nights = 1;
        if (in != null && out != null) {
            nights = java.time.temporal.ChronoUnit.DAYS.between(in, out);
            if (nights <= 0) nights = 1;
        }

        java.math.BigDecimal price = reservation.getRoom().getPricePerNight() == null ? java.math.BigDecimal.ZERO : reservation.getRoom().getPricePerNight();
        java.math.BigDecimal roomCharges = price.multiply(java.math.BigDecimal.valueOf(nights));
        java.math.BigDecimal extra = java.math.BigDecimal.ZERO;
        java.math.BigDecimal discount = java.math.BigDecimal.ZERO;
        java.math.BigDecimal total = roomCharges.add(extra).subtract(discount);

        Billing billing = new Billing();
        billing.setReservation(reservation);
        billing.setRoomCharges(roomCharges);
        billing.setExtraCharges(extra);
        billing.setDiscount(discount);
        billing.setTotalAmount(total);
        billing.setPaymentStatus(Billing.PaymentStatus.PENDING);

        save(billing);
        reservation.setBilling(billing);
        entityManager.merge(reservation);
        return mapToBillingResponse(billing);
    }

    @Override
    public BillingResponse getBilling(Long reservationId) {
        List<Billing> list = entityManager.createNamedQuery("findByReservationId", Billing.class)
                .setParameter("reservationId", reservationId)
                .getResultList();
        if (list.isEmpty()) {
            throw new RuntimeException("Billing not found for reservationId: " + reservationId);
        }
        return mapToBillingResponse(list.get(0));
    }

    private BillingResponse mapToBillingResponse(Billing billing) {
        BillingResponse response = new BillingResponse();
        response.setBillingId(billing.getBillingId());
        response.setReservationId(billing.getReservation().getReservationId());
        response.setRoomCharges(billing.getRoomCharges());
        response.setExtraCharges(billing.getExtraCharges());
        response.setDiscount(billing.getDiscount());
        response.setTotalAmount(billing.getTotalAmount());
        response.setPaymentStatus(billing.getPaymentStatus().name());
        response.setCreatedAt(billing.getCreatedAt());
        return response;
    }
}
