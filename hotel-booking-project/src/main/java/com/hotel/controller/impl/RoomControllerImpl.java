package com.hotel.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.controller.IRoomController;
import com.hotel.dto.DtoRoom;
import com.hotel.service.IRoomService;

@RestController
@RequestMapping("/rest/api/room")
public class RoomControllerImpl implements IRoomController{

    @Autowired
    private IRoomService roomService;

    @GetMapping("/list")
    public ResponseEntity<List<DtoRoom>> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoRoom> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public ResponseEntity<DtoRoom> createRoom(@RequestBody DtoRoom newRoom) {
        return roomService.createRoom(newRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoRoom> updateRoom(@PathVariable Long id, @RequestBody DtoRoom updatedRoom) {
        return roomService.updateRoom(id, updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }
}