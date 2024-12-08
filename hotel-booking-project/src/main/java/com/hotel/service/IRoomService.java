package com.hotel.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.constants.RoomType;
import com.hotel.dto.DtoRoom;

public interface IRoomService {
    
    public ResponseEntity<List<DtoRoom>> getAllRooms();

    public ResponseEntity<DtoRoom> getRoomByRoomNumber(String roomNumber);

    public ResponseEntity<DtoRoom> createRoom(DtoRoom newRoom);

    public ResponseEntity<DtoRoom> updateRoom(String roomNumber, DtoRoom updatedRoom);

    public ResponseEntity<String> deleteRoom(String roomNumber);

    public ResponseEntity<List<DtoRoom>> findAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate);
}
