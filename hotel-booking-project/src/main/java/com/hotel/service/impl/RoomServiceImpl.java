package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hotel.constants.RoomType;
import com.hotel.dto.DtoRoom;
import com.hotel.entities.Booking;
import com.hotel.entities.Room;
import com.hotel.repository.IBookingRepository;
import com.hotel.repository.IRoomRepository;
import com.hotel.service.IRoomService;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IBookingRepository bookingRepository;

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
    public ResponseEntity<DtoRoom> getRoomByRoomNumber(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room != null) {
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
            room.setAvailable(true);
            roomRepository.save(room);

            DtoRoom savedRoom = new DtoRoom();
            BeanUtils.copyProperties(room, savedRoom);
            return ResponseEntity.ok().body(savedRoom);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<DtoRoom> updateRoom(String roomNumber, DtoRoom updatedRoom) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room!=null) {
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
    public ResponseEntity<String> deleteRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
    
        if (room != null) {
            try {
                List<Booking> bookings = bookingRepository.findByRoom_RoomNumber(roomNumber);
                if (bookings.isEmpty()) {
                    roomRepository.delete(room);
                    return ResponseEntity.ok("Room " + roomNumber + " has been deleted successfully.");
                }
                return ResponseEntity.badRequest().body("Room " + roomNumber + " has active bookings and cannot be deleted.");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Room could not be deleted due to server error.");
            }
        }
        return ResponseEntity.badRequest().body("Room " + roomNumber + " could not be found.");
    }

    public ResponseEntity<List<DtoRoom>> findAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> availableRooms = roomRepository.findAvailableRooms(roomType, bedType, hasView, checkInDate, checkOutDate);

        if(!availableRooms.isEmpty()){
            List<DtoRoom> availableDtoRooms = new ArrayList<>();

            for (Room room : availableRooms) {
                DtoRoom dtoRoom = new DtoRoom();

                BeanUtils.copyProperties(room, dtoRoom);
                availableDtoRooms.add(dtoRoom);
            }

            return ResponseEntity.ok().body(availableDtoRooms);
        }

        return ResponseEntity.badRequest().body(null);
    }
}