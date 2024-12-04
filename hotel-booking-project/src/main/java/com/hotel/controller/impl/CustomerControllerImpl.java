package com.hotel.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.controller.ICustomerController;
import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;
import com.hotel.service.ICustomerService;

@RestController
@CrossOrigin
@RequestMapping("rest/api/customer")
public class CustomerControllerImpl implements ICustomerController{

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<DtoCustomer> registerCustomer(@RequestBody DtoCustomerIU customer) {
        return customerService.registerCustomer(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<DtoResponse> loginCustomer(@RequestParam(required = true) String email, @RequestParam(required = true) String password){
        return customerService.loginCustomer(email, password);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DtoCustomer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoCustomer> getCustomerById(@PathVariable(name = "id", required = true) Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoCustomer> updateCustomer(
            @PathVariable(name = "id", required = true) Long id,
            @RequestBody(required = true) DtoCustomerIU updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestParam(name = "oldPassword", required = true) String oldPassword,
            @RequestParam(name = "newPassword", required = true) String newPassword) {
        return customerService.changePassword(id, oldPassword, newPassword);
    }
}
