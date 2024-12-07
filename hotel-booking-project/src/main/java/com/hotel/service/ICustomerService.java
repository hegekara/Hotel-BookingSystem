package com.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;

public interface ICustomerService {

    public ResponseEntity<DtoCustomer> registerCustomer(DtoCustomerIU customer);

    public ResponseEntity<DtoResponse> loginCustomer(String email, String password);

    public ResponseEntity<List<DtoCustomer>> getAllCustomers();

    public ResponseEntity<DtoCustomer> getCustomerByEmail(String email);

    public ResponseEntity<DtoCustomer> updateCustomer(String email, DtoCustomer updatedCustomer);

    public ResponseEntity<String> deleteCustomer(String email);

    public ResponseEntity<String> changePassword(String email, String oldPassword, String newPassword);
}
