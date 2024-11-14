package com.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoRoom {

    private String roomNumber;

    private int capacity;

    private String bedType;

    private double pricePerNight;

    private boolean isAvailable;
    
    private boolean hasView;
}
