package com.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;

/**
 * Customer işlemleri için servis katmanı
 */
public interface ICustomerService {

    /**
     * Yeni bir müşteri kaydı oluşturur.
     *
     * @param customer kaydedilecek müşterinin bilgilerini içeren DtoCustomerIU nesnesi
     * @return DtoCustomer nesnesi ile birlikte bir ResponseEntity döner
     * @see DtoCustomer
     * @see DtoCustomerIU
     */
    public ResponseEntity<DtoCustomer> registerCustomer(DtoCustomerIU customer);


    /**
     * Verilen e-posta ve şifre ile müşterinin giriş yapmasını sağlar.
     *
     * @param email    giriş yapmak isteyen müşterinin e-posta adresi
     * @param password giriş yapmak isteyen müşterinin şifresi
     * @see DtoResponse
     * @return Giriş durumu hakkında bir DtoResponse nesnesi ile birlikte ResponseEntity döner
     */
    public ResponseEntity<DtoResponse> loginCustomer(String id, String password);


    /**
     * Kayıtlı tüm müşterilerin listesini döner.
     *
     * @return Tüm müşterileri içeren DtoCustomer listesini ResponseEntity içinde döner
     * @see DtoCustomer
     */
    public ResponseEntity<List<DtoCustomer>> getAllCustomers();


    /**
     * Verilen e-posta adresine sahip müşteriin bilgilerini döner.
     *
     * @param email bilgileri alınacak müşterinin e-posta adresi
     * @return Bulunan müşteriin bilgilerini içeren DtoCustomer nesnesi ile ResponseEntity döner
     * @see DtoCustomer
     */
    public ResponseEntity<DtoCustomer> getCustomerById(String id);


    /**
     * Verilen e-posta adresine sahip müşteriyi bulur ve bilgilerini günceller.
     *
     * @param email           bilgileri güncellenecek müşterinin e-posta adresi
     * @param updatedCustomer yeni bilgileri içeren DtoCustomerIU nesnesi
     * @return Güncellenmiş müşterinin bilgilerini içeren DtoCustomer nesnesi ile ResponseEntity döner
     * @see DtoCustomer
     */
    public ResponseEntity<DtoCustomer> updateCustomer(String id, DtoCustomer updatedCustomer);


    /**
     * Verilen e-posta adresine sahip müşteriyi siler.
     *
     * @param email silinecek müşterinin e-posta adresi
     * @return Silme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> deleteCustomer(String id);


    /**
     * Müşterinin mevcut şifresini değiştirir.
     *
     * @param email       şifresi değiştirilecek müşterinin e-posta adresi
     * @param oldPassword mevcut şifre
     * @param newPassword yeni şifre
     * @return Şifre değiştirme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> changePassword(String id, String oldPassword, String newPassword);
}
