package com.hotel.repository;
import com.hotel.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
}
