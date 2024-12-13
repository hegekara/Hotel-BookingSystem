package com.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;

/**
 * Personel işlemleri için servis katmanı
 */
public interface IPersonelService {


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
    public ResponseEntity<List<DtoPersonel>> getAllPersonels();


    /**
     * Verilen e-posta adresine sahip personelin bilgilerini döner.
     *
     * @param email bilgileri alınacak personelin e-posta adresi
     * @return Bulunan personelin bilgilerini içeren DtoPersonel nesnesi ile ResponseEntity döner
     * @see DtoPersonel
     */
    public ResponseEntity<DtoPersonel> getPersonelByEmail(String email);


    /**
     * Verilen e-posta adresine sahip personeli bulur ve bilgilerini günceller.
     *
     * @param email           bilgileri güncellenecek personelin e-posta adresi
     * @param updatedPersonel yeni bilgileri içeren DtoPersonelIU nesnesi
     * @return Güncellenmiş personelin bilgilerini içeren DtoPersonel nesnesi ile ResponseEntity döner
     * @see DtoPersonel
     * @see DtoPersonelIU
     */
    public ResponseEntity<DtoPersonel> updatePersonel(String email, DtoPersonelIU updatedPersonel);


    /**
     * Verilen e-posta adresine sahip personeli siler.
     *
     * @param email silinecek personelin e-posta adresi
     * @return Silme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> deletePersonel(String email);


    /**
     * Personelin mevcut şifresini değiştirir.
     *
     * @param email       şifresi değiştirilecek personelin e-posta adresi
     * @param oldPassword mevcut şifre
     * @param newPassword yeni şifre
     * @return Şifre değiştirme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> changePassword(String email, String oldPassword, String newPassword);
}
