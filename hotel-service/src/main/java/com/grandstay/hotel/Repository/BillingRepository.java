package com.grandstay.hotel.Repository;

import com.grandstay.hotel.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing,Long> {
}
