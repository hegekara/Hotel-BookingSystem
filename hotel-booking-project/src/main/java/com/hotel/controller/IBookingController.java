package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoBooking;
import com.hotel.dto.DtoBookingIU;

/**
 * Rezervasyon işlemleri için Controller katmanı arayüzü.
 */
public interface IBookingController {

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
    public ResponseEntity<DtoBooking> getBookingById(Long id);


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
    public ResponseEntity<DtoBooking> updateBooking(Long id, DtoBooking updatedBooking);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip rezervasyonu iptal eder.
     *
     * @param id İptal edilecek rezervasyonun kimliği
     * @return İptal işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> cancelBooking(Long id);


    /**
     * Verilen müşteri e-posta adresine göre o müşteriye ait rezervasyonları getirir.
     *
     * @param email Rezervasyonları getirilecek müşterinin e-posta adresi
     * @return Belirtilen müşteriye ait DtoBooking listesini ResponseEntity ile döner
     * @see DtoBooking
     */
    public ResponseEntity<List<DtoBooking>> getBookingsByCustomerEmail(String email);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip bir rezervasyonu onaylar.
     *
     * @param id Onaylanacak rezervasyonun kimliği
     * @return Onaylama işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> acceptReservation(Long id);


    /**
     * Verilen rezervasyon kimliğine (ID) sahip bir rezervasyonu reddeder.
     *
     * @param id Reddedilecek rezervasyonun kimliği
     * @return Reddetme işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> rejcetReservation(Long id);
}
