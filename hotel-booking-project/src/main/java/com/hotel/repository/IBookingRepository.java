package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.Booking;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByCustomerId(Long customerId);
}
