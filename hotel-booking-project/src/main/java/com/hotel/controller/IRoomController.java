package com.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.constants.RoomType;
import com.hotel.dto.DtoRoom;

public interface IRoomController {

    public ResponseEntity<List<DtoRoom>> getAllRooms();

    public ResponseEntity<DtoRoom> getRoomById(Long id);

    public ResponseEntity<DtoRoom> createRoom(DtoRoom newRoom);

    public ResponseEntity<DtoRoom> updateRoom(Long id, DtoRoom updatedRoom);

    public ResponseEntity<String> deleteRoom(Long id);

    public ResponseEntity<List<DtoRoom>> getAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate);

    public ResponseEntity<List<String>> getRoomTypes();
}
