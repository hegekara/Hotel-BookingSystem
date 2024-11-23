package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoRoom;

public interface IRoomController {

    public ResponseEntity<List<DtoRoom>> getAllRooms();

    public ResponseEntity<DtoRoom> getRoomById(Long id);

    public ResponseEntity<DtoRoom> createRoom(DtoRoom newRoom);

    public ResponseEntity<DtoRoom> updateRoom(Long id, DtoRoom updatedRoom);

    public ResponseEntity<String> deleteRoom(Long id);
}
