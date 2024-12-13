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


    /**
     * Belirtilen kriterlere uygun ve rezerve edilmemiş olan odaları getirir.
     *
     * @param roomType     Aranan oda türü (ör. STANDART, SUIT). `null` gönderilirse herhangi bir tür kabul edilir.
     * @param bedType      Aranan yatak türü (ör. SINGLE, DOUBLE). `null` gönderilirse herhangi bir yatak türü kabul edilir.
     * @param hasView      Odanın manzaralı olup olmadığı. `null` gönderilirse herhangi biri kabul edilir.
     * @param checkInDate  Giriş tarihi. Bu tarihte çakışan bir rezervasyon olmamalıdır.
     * @param checkOutDate Çıkış tarihi. Bu tarihte çakışan bir rezervasyon olmamalıdır.
     * @return Belirtilen kriterlere uygun ve müsait olan odaların bir listesini döner
     */
   @Query("SELECT r FROM Room r WHERE (:roomType IS NULL OR r.roomType = :roomType) " + 
    "AND (:bedType IS NULL OR r.bedType = :bedType) " +
    "AND (:hasView IS NULL OR r.hasView = :hasView) " +
    "AND r.isAvailable = true " +
    "AND r.id NOT IN ( SELECT b.room.id FROM Booking b WHERE b.checkOutDate >= :checkInDate AND b.checkInDate <= :checkOutDate) ")
    List<Room> findAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Verilen oda numarasına göre bir oda getirir.
     *
     * @param roomNumber Oda numarası
     * @return Verilen oda numarasına sahip Room nesnesini döner. Bulunamazsa `null` döner.
     */
    Room findByRoomNumber(String roomNumber);
}
