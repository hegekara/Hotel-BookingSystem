package com.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hotel.dto.DtoRoom;
import com.hotel.entities.Room;
import com.hotel.repository.IRoomRepository;
import com.hotel.service.IRoomService;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    @Override
    public ResponseEntity<List<DtoRoom>> getAllRooms() {
        List<Room> listRoom = roomRepository.findAll();
        if (!listRoom.isEmpty()) {
            List<DtoRoom> dtoRoomList = new ArrayList<>();
            for (Room room : listRoom) {
                DtoRoom dtoRoom = new DtoRoom();
                BeanUtils.copyProperties(room, dtoRoom);
                dtoRoomList.add(dtoRoom);
            }
            return ResponseEntity.ok().body(dtoRoomList);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<DtoRoom> getRoomById(Long id) {
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isPresent()) {
            Room room = optional.get();
            DtoRoom dtoRoom = new DtoRoom();
            BeanUtils.copyProperties(room, dtoRoom);
            return ResponseEntity.ok().body(dtoRoom);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<DtoRoom> createRoom(DtoRoom newRoom) {
        try {
            Room room = new Room();

            BeanUtils.copyProperties(newRoom, room);
            roomRepository.save(room);

            DtoRoom savedRoom = new DtoRoom();
            BeanUtils.copyProperties(room, savedRoom);
            return ResponseEntity.ok().body(savedRoom);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<DtoRoom> updateRoom(Long id, DtoRoom updatedRoom) {
        Optional<Room> option = roomRepository.findById(id);

        if (option.isPresent()) {
            Room room = option.get();
            room.setRoomNumber(updatedRoom.getRoomNumber());
            room.setAvailable(updatedRoom.isAvailable());
            room.setBedType(updatedRoom.getBedType());
            room.setCapacity(updatedRoom.getCapacity());
            room.setHasView(updatedRoom.isHasView());
            room.setPricePerNight(updatedRoom.getPricePerNight());

            try {
                roomRepository.save(room);
                DtoRoom savedRoom = new DtoRoom();
                BeanUtils.copyProperties(room, savedRoom);
                return ResponseEntity.ok().body(savedRoom);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }
        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<String> deleteRoom(Long id) {
        Optional<Room> option = roomRepository.findById(id);

        if (option.isPresent()) {
            Room room = option.get();
            try {
                roomRepository.delete(room);
                return ResponseEntity.ok("Room with ID " + id + " has been deleted successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Room could not be deleted due to server error.");
            }
        }
        return ResponseEntity.badRequest().body("Room with ID " + id + " could not be found.");
    }
}