package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;

/**
 * Personel işlemleri için kontroller katmanı
 */
public interface IPersonelController {

    /**
     * Yeni bir personel kaydı oluşturur.
     *
     * @param personel kaydedilecek personelin bilgilerini içeren DtoPersonelIU nesnesi
     * @return DtoPersonel nesnesi ile birlikte bir ResponseEntity döner
     * @see DtoPersonel
     * @see DtoPersonelIU
     */
    public ResponseEntity<DtoPersonel> registerPersonel(DtoPersonelIU personel);


    /**
     * Verilen e-posta ve şifre ile personelin giriş yapmasını sağlar.
     *
     * @param email    giriş yapmak isteyen personelin e-posta adresi
     * @param password giriş yapmak isteyen personelin şifresi
     * @see DtoResponse
     * @return Giriş durumu hakkında bir DtoResponse nesnesi ile birlikte ResponseEntity döner
     */
    public ResponseEntity<DtoResponse> loginPersonel(String email, String password);


    /**
     * Kayıtlı tüm personellerin listesini döner.
     *
     * @see DtoPersonel
     * @return Tüm personelleri içeren DtoPersonel listesini ResponseEntity içinde döner
     */
    public ResponseEntity<List<DtoPersonel>> getAllPersonel();


    /**
     * Verilen id bilgisine sahip personelin bilgilerini döner.
     *
     * @param id bilgileri alınacak personelin id bilgisi
     * @return Bulunan personelin bilgilerini içeren DtoPersonel nesnesi ile ResponseEntity döner
     * @see DtoPersonel
     */
    public ResponseEntity<DtoPersonel> getPersonelById(String id);


    /**
     * Verilen id bilgisine sahip personeli bulur ve bilgilerini günceller.
     *
     * @param id           bilgileri güncellenecek personelin id bilgisi
     * @param updatedPersonel yeni bilgileri içeren DtoPersonelIU nesnesi
     * @return Güncellenmiş personelin bilgilerini içeren DtoPersonel nesnesi ile ResponseEntity döner
     * @see DtoPersonel
     * @see DtoPersonelIU
     */
    public ResponseEntity<DtoPersonel> updatePersonel(String id, DtoPersonelIU updatedPersonel);


    /**
     * Verilen id bilgisine sahip personeli siler.
     *
     * @param id silinecek personelin id bilgisi
     * @return Silme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> deletePersonel(String id);


    /**
     * Personelin mevcut şifresini değiştirir.
     *
     * @param id       şifresi değiştirilecek personelin id bilgisi
     * @param oldPassword mevcut şifre
     * @param newPassword yeni şifre
     * @return Şifre değiştirme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> changePassword(String id, String oldPassword, String newPassword);


    /**
     * Kullanıcının kayıt olabileceği rol listesini döndürür.
     * 
     * @return Rolleri belirten bir liste döndürür.
     */
    public ResponseEntity<List<String>> getRoles();

}
