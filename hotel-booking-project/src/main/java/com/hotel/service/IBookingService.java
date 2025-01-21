package com.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoBooking;
import com.hotel.dto.DtoBookingIU;

/**
 * Rezervasyon işlemleri için servis katmanı arayüzü.
 */
public interface IBookingService {

    /**
     * Sistemdeki tüm rezervasyonları getirir.
     *
     * @return Tüm rezervasyonları içeren DtoBooking listesini ResponseEntity ile döner
     * @see DtoBooking
     */
    public ResponseEntity<List<DtoBooking>> getAllBookings();


    /**
     * Verilen rezervasyon kimliğine (ID) göre rezervasyon bilgilerini getirir.
     *
     * @param id Bilgileri alınacak rezervasyonun kimliği
     * @return Bulunan rezervasyon bilgilerini içeren DtoBooking nesnesini ResponseEntity ile döner
     * @see DtoBooking
     */
    public ResponseEntity<DtoBooking> getBookingById(String id);


    /**
     * Yeni bir rezervasyon oluşturur.
     *
     * @param dtoBookingIU Oluşturulacak rezervasyon bilgilerini içeren DtoBookingIU nesnesi
     * @return Oluşturulan rezervasyon bilgilerini içeren DtoBookingIU nesnesini ResponseEntity ile döner
     * @see DtoBookingIU
     */
    public ResponseEntity<DtoBookingIU> createBooking(DtoBookingIU dtoBookingIU);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip bir rezervasyonu günceller.
     *
     * @param id             Güncellenecek rezervasyonun kimliği
     * @param updatedBooking Güncellenmiş rezervasyon bilgilerini içeren DtoBooking nesnesi
     * @return Güncellenmiş rezervasyon bilgilerini içeren DtoBooking nesnesini ResponseEntity ile döner
     * @see DtoBooking
     */
    public ResponseEntity<DtoBooking> updateBooking(String id, DtoBooking updatedBooking);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip rezervasyonu iptal eder.
     *
     * @param id İptal edilecek rezervasyonun kimliği
     * @return İptal işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> cancelBooking(String id);


    /**
     * Verilen müşteri id bilgisine göre o müşteriye ait rezervasyonları getirir.
     *
     * @param id Rezervasyonları getirilecek müşterinin id bilgisi
     * @return Belirtilen müşteriye ait DtoBooking listesini ResponseEntity ile döner
     * @see DtoBooking
     */
    public ResponseEntity<List<DtoBooking>> getBookingsByCustomerId(String email);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip bir rezervasyonu onaylar.
     *
     * @param id Onaylanacak rezervasyonun kimliği
     * @return Onaylama işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> acceptReservation(String id);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip bir rezervasyonu reddeder.
     *
     * @param id Reddedilecek rezervasyonun kimliği
     * @return Reddetme işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> rejectReservation(String id);
}
