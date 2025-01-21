package com.hotel.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.user.Customer;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID>{

    /**
     * e-posta adresine göre müşteriyi veritabanından çeker
     * 
     * @param email
     * @return parametre olarak girilen e-posta ile eşleşen müşteri döndürülür.
     */
    Customer findByEmail(String email);
}
