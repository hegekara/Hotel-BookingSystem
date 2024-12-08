package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hotel.constants.BookingStatus;
import com.hotel.dto.DtoBooking;
import com.hotel.dto.DtoBookingIU;
import com.hotel.entities.Booking;
import com.hotel.entities.Room;
import com.hotel.entities.user.Customer;
import com.hotel.repository.IBookingRepository;
import com.hotel.repository.ICustomerRepository;
import com.hotel.repository.IRoomRepository;
import com.hotel.service.IBookingService;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IRoomRepository roomRepository;


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
    public ResponseEntity<DtoBookingIU> createBooking(DtoBookingIU dtoBookingIU) {
        try {
            Customer customer = customerRepository.findByEmail(dtoBookingIU.getEmail());

            Room room = roomRepository.findByRoomNumber(dtoBookingIU.getRoomNumber());

            if (!room.isAvailable()) {
                throw new IllegalStateException("Room is not available for booking");
            }

            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setRoom(room);
            booking.setCheckInDate(dtoBookingIU.getCheckInDate());
            booking.setCheckOutDate(dtoBookingIU.getCheckOutDate());
            booking.setStatus(BookingStatus.WAITING);
            booking.setBookingDate(LocalDate.now());

            bookingRepository.save(booking);

            return ResponseEntity.ok().body(dtoBookingIU);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
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
                return ResponseEntity.badRequest().body(null);
            }
        }

        return ResponseEntity.status(404).body(null);
    }

    @Override
    public ResponseEntity<String> cancelBooking(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        System.out.println("5");

        if (optionalBooking.isPresent()) {
            try {
                System.out.println("4");
                bookingRepository.delete(optionalBooking.get());
                return ResponseEntity.ok("Booking has been canceled successfully.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to cancel the booking.");
            }
        }

        return ResponseEntity.status(404).body("Booking not found.");
    }

    @Override
    public ResponseEntity<List<DtoBooking>> getBookingsByCustomerEmail(String email) {

        List<Booking> bookingList = bookingRepository.findByCustomer_Email(email);

        if (!bookingList.isEmpty()) {
            List<DtoBooking> dtoBookingList = bookingList.stream().map(booking -> {
                DtoBooking dtoBooking = new DtoBooking();
                BeanUtils.copyProperties(booking, dtoBooking);
                return dtoBooking;
            }).collect(Collectors.toList());

            return ResponseEntity.ok().body(dtoBookingList);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<String> acceptReservation(Long id) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
    
            if (optionalBooking.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with id: " + id);
            }
            
            Booking booking = optionalBooking.get();
            booking.setStatus(BookingStatus.ACCEPTED);
            bookingRepository.save(booking);
    
            return ResponseEntity.ok("Room reservation accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while accepting the reservation.");
        }
    }


    @Override
    public ResponseEntity<String> rejectReservation(Long id) {
        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            
            if (optionalBooking.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with id: " + id);
            }
            
            Booking booking = optionalBooking.get();
            booking.setStatus(BookingStatus.REJECTED);
            bookingRepository.save(booking);
            bookingRepository.delete(booking);
            
            return ResponseEntity.ok("Room reservation rejected successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while rejecting the reservation.");
        }
    }
}