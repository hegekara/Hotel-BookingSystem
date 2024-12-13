package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.Booking;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long>{

    /**
     * Kullanıcı e-postasına göre yapılan rezervasyonları veritabanında arar
     * 
     * @param email
     * @return Kullanıcının yapmış oluduğu rezervasyonları döndürür
     */
    List<Booking> findByCustomer_Email(String email);

    /**
     * Oda numarasına göre yapılan rezervasyonları veritabanında arar
     * 
     * @param roomNumber
     * @return Odaya yapılmış olan rezervasyonları döndürür
     */
    List<Booking> findByRoom_RoomNumber(String roomNumber);
}
