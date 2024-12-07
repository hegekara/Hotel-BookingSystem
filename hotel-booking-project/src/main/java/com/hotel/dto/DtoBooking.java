package com.hotel.dto;

import java.time.LocalDate;

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

    private Long id;

    private Customer customer;

    private Room room;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BookingStatus status;
}
