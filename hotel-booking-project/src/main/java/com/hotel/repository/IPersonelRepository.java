package com.hotel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.user.Personel;


@Repository
public interface IPersonelRepository extends JpaRepository<Personel, Long>{

    /**
     * e-posta adresine göre personeli veritabanından çeker
     * 
     * @param email
     * @return parametre olarak girilen e-posta ile eşleşen personel döndürülür.
     */
    Personel findByEmail(String email);
}
