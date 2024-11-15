package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoCustomer;
import com.hotel.dto.DtoCustomerIU;

public interface ICustomerController {

    public ResponseEntity<DtoCustomer> registerCustomer(DtoCustomerIU customer);

    public ResponseEntity<DtoCustomer> loginCustomer(String email, String password);

    public ResponseEntity<List<DtoCustomer>> getAllCustomers();

    public ResponseEntity<DtoCustomer> getCustomerById(Long id);

    public ResponseEntity<DtoCustomer> updateCustomer(Long id, DtoCustomerIU updatedCustomer);

    public ResponseEntity<String> deleteCustomer(Long id);

    public ResponseEntity<String> changePassword(Long id, String oldPassword, String newPassword);
            
}
