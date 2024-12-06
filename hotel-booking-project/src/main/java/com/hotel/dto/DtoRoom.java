package com.hotel.dto;

import com.hotel.constants.BedType;
import com.hotel.constants.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoRoom {

    private String roomNumber;

    private RoomType roomType;

    private int capacity;

    private BedType bedType;

    private double pricePerNight;

    private boolean isAvailable;
    
    private boolean hasView;
}
