package com.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel.constants.RoomType;
import com.hotel.entities.Room;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long>{

   @Query("SELECT r FROM Room r WHERE (:roomType IS NULL OR r.roomType = :roomType) " + 
    "AND (:bedType IS NULL OR r.bedType = :bedType) " +
    "AND (:hasView IS NULL OR r.hasView = :hasView) " +
    "AND r.isAvailable = true " +
    "AND r.id NOT IN ( SELECT b.room.id FROM Booking b WHERE b.checkOutDate >= :checkInDate AND b.checkInDate <= :checkOutDate) ")
    List<Room> findAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate);

    Room findByRoomNumber(String roomNumber);
}
