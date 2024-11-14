package com.hotel.dto;

import java.time.LocalDate;

import com.hotel.entities.Customer;
import com.hotel.entities.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoBooking {

    private Customer customer;

    private Room room;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String status;
}
