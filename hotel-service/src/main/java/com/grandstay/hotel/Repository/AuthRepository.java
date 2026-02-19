package com.grandstay.hotel.Repository;

import java.util.Optional;

import com.grandstay.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);
}

