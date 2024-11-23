package com.hotel.dto;

import com.hotel.constants.BedType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoRoom {

    private String roomNumber;

    private int capacity;

    private BedType bedType;

    private double pricePerNight;

    private boolean isAvailable;
    
    private boolean hasView;
}
