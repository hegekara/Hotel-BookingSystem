package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoBooking;

public interface IBookingController {
    public ResponseEntity<List<DtoBooking>> getAllBookings();

    public ResponseEntity<DtoBooking> getBookingById(Long id);

    public ResponseEntity<DtoBooking> createBooking(DtoBooking newBooking);

    public ResponseEntity<DtoBooking> updateBooking(Long id, DtoBooking updatedBooking);

    public ResponseEntity<String> cancelBooking(Long id);

    public ResponseEntity<List<DtoBooking>> getBookingsByCustomerId(Long customerId);
}
