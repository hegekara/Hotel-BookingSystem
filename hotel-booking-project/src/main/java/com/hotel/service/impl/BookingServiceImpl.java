package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hotel.dto.DtoBooking;
import com.hotel.entities.Booking;
import com.hotel.repository.IBookingRepository;
import com.hotel.service.IBookingService;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;


    @Override
    public ResponseEntity<List<DtoBooking>> getAllBookings() {
        List<Booking> bookingList = bookingRepository.findAll();

        if (!bookingList.isEmpty()) {
            List<DtoBooking> dtoBookingList = new ArrayList<>();

            for (Booking booking : bookingList) {
                DtoBooking dtoBooking = new DtoBooking();
                BeanUtils.copyProperties(booking, dtoBooking);
                dtoBookingList.add(dtoBooking);
            }

            return ResponseEntity.ok().body(dtoBookingList);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<DtoBooking> getBookingById(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            DtoBooking dtoBooking = new DtoBooking();
            BeanUtils.copyProperties(booking, dtoBooking);
            return ResponseEntity.ok().body(dtoBooking);
        }

        return ResponseEntity.status(404).body(null);
    }

    @Override
    public ResponseEntity<DtoBooking> createBooking(DtoBooking newBooking) {
        try {
            Booking booking = new Booking();
            BeanUtils.copyProperties(newBooking, booking);

            booking = bookingRepository.save(booking);
            booking.setBookingDate(LocalDate.now());

            DtoBooking savedBooking = new DtoBooking();
            BeanUtils.copyProperties(booking, savedBooking);
            return ResponseEntity.ok().body(savedBooking);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<DtoBooking> updateBooking(Long id, DtoBooking updatedBooking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            booking.setRoom(updatedBooking.getRoom());
            booking.setCheckInDate(updatedBooking.getCheckInDate());
            booking.setCheckOutDate(updatedBooking.getCheckOutDate());
            booking.setCustomer(updatedBooking.getCustomer());

            try {
                booking = bookingRepository.save(booking);

                DtoBooking savedBooking = new DtoBooking();
                BeanUtils.copyProperties(booking, savedBooking);
                return ResponseEntity.ok().body(savedBooking);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }

        return ResponseEntity.status(404).body(null);
    }

    @Override
    public ResponseEntity<String> cancelBooking(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            try {
                bookingRepository.delete(optionalBooking.get());
                return ResponseEntity.ok("Booking with ID " + id + " has been canceled successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to cancel the booking.");
            }
        }

        return ResponseEntity.status(404).body("Booking not found.");
    }

    @Override
    public ResponseEntity<List<DtoBooking>> getBookingsByCustomerId(Long customerId) {
        List<Booking> bookingList = bookingRepository.findByCustomerId(customerId);

        if (!bookingList.isEmpty()) {
            List<DtoBooking> dtoBookingList = new ArrayList<>();
            for (Booking booking : bookingList) {
                DtoBooking dtoBooking = new DtoBooking();
                BeanUtils.copyProperties(booking, dtoBooking);
                dtoBookingList.add(dtoBooking);
            }
            return ResponseEntity.ok().body(dtoBookingList);
        }

        return ResponseEntity.status(404).body(null);
    }
}