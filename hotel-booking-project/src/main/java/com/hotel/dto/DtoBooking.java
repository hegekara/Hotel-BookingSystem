package com.hotel.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.hotel.constants.BookingStatus;
import com.hotel.entities.Room;
import com.hotel.entities.user.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoBooking {

    private UUID id;

    private Customer customer;

    private Room room;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BookingStatus status;
}
