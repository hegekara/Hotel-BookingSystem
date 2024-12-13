package com.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hotel.constants.BedType;
import com.hotel.constants.RoomType;
import com.hotel.dto.DtoRoom;
import com.hotel.entities.Room;
import com.hotel.repository.IBookingRepository;
import com.hotel.repository.IRoomRepository;
import com.hotel.service.impl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private IRoomRepository roomRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = Arrays.asList(
                new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true),
                new Room(2L, "102", RoomType.DELUXE, 3, BedType.DOUBLE, 200.0, false, false)
        );

        when(roomRepository.findAll()).thenReturn(rooms);

        ResponseEntity<List<DtoRoom>> response = roomService.getAllRooms();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetRoomByRoomNumber() {
        Room room = new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);

        when(roomRepository.findByRoomNumber("101")).thenReturn(room);

        ResponseEntity<DtoRoom> response = roomService.getRoomByRoomNumber("101");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("101", response.getBody().getRoomNumber());
    }

    @Test
    public void testCreateRoom() {
        Room room = new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);
        DtoRoom dtoRoom = new DtoRoom("101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        ResponseEntity<DtoRoom> response = roomService.createRoom(dtoRoom);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("101", response.getBody().getRoomNumber());
    }

    @Test
    public void testUpdateRoom() {
        Room existingRoom = new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);
        DtoRoom updatedDtoRoom = new DtoRoom("101", RoomType.STANDART, 3, BedType.DOUBLE, 200.0, false, false);

        when(roomRepository.findByRoomNumber("101")).thenReturn(existingRoom);
        when(roomRepository.save(any(Room.class))).thenReturn(existingRoom);

        ResponseEntity<DtoRoom> response = roomService.updateRoom("101", updatedDtoRoom);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RoomType.STANDART, response.getBody().getRoomType());
        assertEquals(200.0, response.getBody().getPricePerNight(), 0.0);
    } 


    @Test
    public void testDeleteRoom() {
        Room room = new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);

        when(roomRepository.findByRoomNumber("101")).thenReturn(room);
        when(bookingRepository.findByRoom_RoomNumber("101")).thenReturn(Collections.emptyList());
        doNothing().when(roomRepository).delete(any(Room.class));

        ResponseEntity<String> response = roomService.deleteRoom("101");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Room 101 has been deleted successfully.", response.getBody());
    }

    @Test
    public void testFindAvailableRooms() {
        Room room = new Room(1L, "101", RoomType.STANDART, 2, BedType.SINGLE, 100.0, true, true);

        when(roomRepository.findAvailableRooms(
                RoomType.STANDART, "SINGLE", true, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10)
        )).thenReturn(Collections.singletonList(room));

        ResponseEntity<List<DtoRoom>> response = roomService.findAvailableRooms(
                RoomType.STANDART, "SINGLE", true, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10)
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("101", response.getBody().get(0).getRoomNumber());
    }
}