package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;

/**
 * Customer işlemleri için kontroller katmanı
 */
public interface ICustomerController {

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
    public ResponseEntity<DtoResponse> loginCustomer(String email, String password);


    /**
     * Kayıtlı tüm müşterilerin listesini döner.
     *
     * @see DtoCustomer
     * @return Tüm müşterileri içeren DtoCustomer listesini ResponseEntity içinde döner
     */
    public ResponseEntity<List<DtoCustomer>> getAllCustomers();


    /**
     * Verilen id bilgisine sahip müşteriin bilgilerini döner.
     *
     * @param id bilgileri alınacak müşterinin id bilgisi
     * @return Bulunan müşteriin bilgilerini içeren DtoCustomer nesnesi ile ResponseEntity döner
     * @see DtoCustomer
     */
    public ResponseEntity<DtoCustomer> getCustomerById(String id);


    /**
     * Verilen id bilgisine sahip müşteriyi bulur ve bilgilerini günceller.
     *
     * @param id           bilgileri güncellenecek müşterinin id bilgisi
     * @param updatedCustomer yeni bilgileri içeren DtoCustomerIU nesnesi
     * @return Güncellenmiş müşterinin bilgilerini içeren DtoCustomer nesnesi ile ResponseEntity döner
     * @see DtoCustomer
     */
    public ResponseEntity<DtoCustomer> updateCustomer(String id, DtoCustomer updatedCustomer);


    /**
     * Verilen id bilgisine sahip müşteriyi siler.
     *
     * @param id silinecek müşterinin id bilgisi
     * @return Silme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> deleteCustomer(String id);


    /**
     * Müşterinin mevcut şifresini değiştirir.
     *
     * @param id       şifresi değiştirilecek müşterinin id bilgisi
     * @param oldPassword mevcut şifre
     * @param newPassword yeni şifre
     * @return Şifre değiştirme işleminin sonucunu belirten bir mesaj ile ResponseEntity döner
     */
    public ResponseEntity<String> changePassword(String id, String oldPassword, String newPassword);
            
}
