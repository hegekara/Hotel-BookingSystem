package com.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.constants.RoomType;
import com.hotel.dto.DtoRoom;

/**
 * Oda işlemleri için Controller katmanı arayüzü.
 */
public interface IRoomController {

    /**
     * Sistemdeki tüm odaları getirir.
     *
     * @return Tüm odaları içeren DtoRoom listesini ResponseEntity ile döner
     * @see DtoRoom
     */
    public ResponseEntity<List<DtoRoom>> getAllRooms();


    /**
     * Verilen oda numarasına göre oda bilgilerini getirir.
     *
     * @param roomNumber Bilgileri alınacak odanın numarası
     * @return Bulunan odanın bilgilerini içeren DtoRoom nesnesini ResponseEntity ile döner
     * @see DtoRoom
     */
    public ResponseEntity<DtoRoom> getRoomById(String id);


    /**
     * Yeni bir oda oluşturur.
     *
     * @param newRoom Oluşturulacak odanın bilgilerini içeren DtoRoom nesnesi
     * @return Oluşturulan odanın bilgilerini içeren DtoRoom nesnesini ResponseEntity ile döner
     * @see DtoRoom
     */
    public ResponseEntity<DtoRoom> createRoom(DtoRoom newRoom);


    /**
     * Verilen oda numarasına sahip bir odanın bilgilerini günceller.
     *
     * @param roomNumber   Güncellenecek odanın numarası
     * @param updatedRoom  Güncellenmiş oda bilgilerini içeren DtoRoom nesnesi
     * @return Güncellenmiş odanın bilgilerini içeren DtoRoom nesnesini ResponseEntity ile döner
     * @see DtoRoom
     */
    public ResponseEntity<DtoRoom> updateRoom(String id, DtoRoom updatedRoom);

    
    /**
     * Verilen oda numarasına sahip bir odayı sistemden siler.
     *
     * @param roomNumber Silinecek odanın numarası
     * @return Silme işleminin sonucunu belirten bir mesajı ResponseEntity ile döner
     */
    public ResponseEntity<String> deleteRoom(String id);


    /**
     * Belirtilen kriterlere uygun ve uygun durumda olan odaları getirir.
     *
     * @param roomType      İstenen oda türü (ör. STANDART, SUIT)
     * @param bedType       İstenen yatak türü (ör. SINGLE, DOUBLE)
     * @param hasView       Odanın manzaralı olup olmadığı bilgisi
     * @param checkInDate   Giriş tarihi
     * @param checkOutDate  Çıkış tarihi
     * @return Belirtilen kriterlere uygun odaları içeren DtoRoom listesini ResponseEntity ile döner
     * @see DtoRoom
     */
    public ResponseEntity<List<DtoRoom>> getAvailableRooms(RoomType roomType, String bedType, Boolean hasView, LocalDate checkInDate, LocalDate checkOutDate);


    /**
     * Sistemde tanımlı tüm oda türlerini getirir.
     *
     * @return Oda türlerinin listesini ResponseEntity ile döner
     */
    public ResponseEntity<List<String>> getRoomTypes();


    /**
     * Sistemde tanımlı tüm yatak türlerini getirir.
     *
     * @return Yatak türlerinin listesini ResponseEntity ile döner
     */
    public ResponseEntity<List<String>> getBedTypes();
}
