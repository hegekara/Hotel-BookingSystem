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

    public ResponseEntity<DtoCustomer> getCustomerById(Long id);

    public ResponseEntity<DtoCustomer> updateCustomer(Long id, DtoCustomerIU updatedCustomer);

    public ResponseEntity<String> deleteCustomer(Long id);

    public ResponseEntity<String> changePassword(Long id, String oldPassword, String newPassword);
}
