package com.hotel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.user.Customer;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
}
