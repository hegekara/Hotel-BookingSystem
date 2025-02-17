package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.constants.Role;
import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;
import com.hotel.entities.Booking;
import com.hotel.entities.user.Customer;
import com.hotel.repository.IBookingRepository;
import com.hotel.repository.ICustomerRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<DtoCustomer> registerCustomer(DtoCustomerIU dtoCustomerIU) {

        try {
            String encryptedPassword = passwordEncoder.encode(dtoCustomerIU.getPassword());
            dtoCustomerIU.setPassword(encryptedPassword);
    
            Customer customer = new Customer();
    
            BeanUtils.copyProperties(dtoCustomerIU, customer);
            customer.setRegisterDate(LocalDate.now());
            customer.setRole(Role.CUSTOMER);
            customerRepository.save(customer);
    
            DtoCustomer dtoCustomer = new DtoCustomer();
            BeanUtils.copyProperties(customer, dtoCustomer);
    
            return ResponseEntity.ok().body(dtoCustomer);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @Override
    public ResponseEntity<DtoResponse> loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            if(passwordEncoder.matches(password, customer.getPassword())){
                DtoCustomer dtoCustomer = new DtoCustomer();
                BeanUtils.copyProperties(customer, dtoCustomer);
                
                String token = jwtUtil.generateToken(email, customer.getRole());
                return ResponseEntity.ok(new DtoResponse(dtoCustomer, token, "login succsessful"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    @Override
    public ResponseEntity<List<DtoCustomer>> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        {
            List<DtoCustomer> dtoCustomerList = new ArrayList<>();
            for (Customer item : customerList) {
                DtoCustomer dtoCustomer = new DtoCustomer();
                BeanUtils.copyProperties(item, dtoCustomer);
                dtoCustomerList.add(dtoCustomer);
            }

            return ResponseEntity.ok().body(dtoCustomerList);
        }
    }


    @Override
    public ResponseEntity<DtoCustomer> getCustomerById(String id) {
        Optional<Customer> optional = customerRepository.findById(UUID.fromString(id));
        
        if(optional.isPresent()){
            Customer customer = optional.get();
            DtoCustomer dtoCustomer = new DtoCustomer();

            BeanUtils.copyProperties(customer, dtoCustomer);
            return ResponseEntity.ok().body(dtoCustomer);
        }
        return ResponseEntity.badRequest().body(null);
    }


    @Override
    public ResponseEntity<DtoCustomer> updateCustomer(String id, DtoCustomer updatedCustomer) {
        Optional<Customer> optional = customerRepository.findById(UUID.fromString(id));
    
        if (optional.isPresent()) {
            Customer customer = optional.get();
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());

            Customer updatedEntity = customerRepository.save(customer);

            DtoCustomer dtoCustomer = new DtoCustomer();
            BeanUtils.copyProperties(updatedEntity, dtoCustomer);
    
            return ResponseEntity.ok(dtoCustomer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    @Override
    public ResponseEntity<String> deleteCustomer(String id) {
        Optional<Customer> optional = customerRepository.findById(UUID.fromString(id));
    
        if (optional.isPresent()) {
            Customer existingCustomer = optional.get();
            List<Booking> bookings = bookingRepository.findByCustomer_Email(existingCustomer.getEmail());
    
            if (bookings.isEmpty()) {
                customerRepository.deleteById(existingCustomer.getId());
                return ResponseEntity.ok("Customer has been deleted successfully.");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer has active bookings and cannot be deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
    }


    @Override
    public ResponseEntity<String> changePassword(String id, String oldPassword, String newPassword) {
        Optional<Customer> optional = customerRepository.findById(UUID.fromString(id));
    
        if (optional.isPresent()) {
    
            Customer customer = optional.get();
            if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerRepository.save(customer);
    
                return ResponseEntity.ok("Password updated successfully.");
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password does not match.");
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
    }
}
