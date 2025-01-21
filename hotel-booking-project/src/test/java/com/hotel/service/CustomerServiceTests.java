package com.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.constants.Role;
import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoCustomer;
import com.hotel.dto.user.DtoCustomerIU;
import com.hotel.entities.user.Customer;
import com.hotel.repository.IBookingRepository;
import com.hotel.repository.ICustomerRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testRegisterCustomer_Success() {
        DtoCustomerIU mockDto = new DtoCustomerIU("John", "Doe", "john.doe@example.com", "123456789", "password123");
        String encryptedPassword = "$2a$10$encryptedPassword";
        when(passwordEncoder.encode(mockDto.getPassword())).thenReturn(encryptedPassword);

        Customer mockCustomer = new Customer();
        BeanUtils.copyProperties(mockDto, mockCustomer);
        mockCustomer.setPassword(encryptedPassword);
        mockCustomer.setRegisterDate(LocalDate.now());
        mockCustomer.setRole(Role.CUSTOMER);

        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        ResponseEntity<DtoCustomer> response = customerService.registerCustomer(mockDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockDto.getEmail(), response.getBody().getEmail());
        assertEquals(Role.CUSTOMER, response.getBody().getRole());
    }

    @Test
    void testLoginCustomer_Success() {
        String email = "john.doe@example.com";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPassword";
        UUID id = UUID.randomUUID();

        Customer mockCustomer = new Customer(id, "John", "Doe", email, Role.CUSTOMER, "123456789", encodedPassword, LocalDate.now());
        when(customerRepository.findByEmail(email)).thenReturn(mockCustomer);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        String jwtToken = "mockJwtToken";
        when(jwtUtil.generateToken(email, Role.CUSTOMER)).thenReturn(jwtToken);

        ResponseEntity<DtoResponse> response = customerService.loginCustomer(email, rawPassword);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jwtToken, response.getBody().getToken());
        assertEquals("login succsessful", response.getBody().getMessage());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> mockCustomers = List.of(
            new Customer(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", Role.CUSTOMER, "123456789", "$2a$10$password", LocalDate.now()),
            new Customer(UUID.randomUUID(), "Jane", "Smith", "jane.smith@example.com", Role.CUSTOMER, "987654321", "$2a$10$password", LocalDate.now())
        );
        when(customerRepository.findAll()).thenReturn(mockCustomers);

        ResponseEntity<List<DtoCustomer>> response = customerService.getAllCustomers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testUpdateCustomer() {
        String email = "john.doe@example.com";
        UUID id = UUID.randomUUID();

        Customer mockCustomer = new Customer(id, "John", "Doe", email, Role.CUSTOMER, "123456789", "$2a$10$password", LocalDate.now());

        DtoCustomer updatedDto = new DtoCustomer(id, "Johnny", "Doe", email, "987654321", Role.CUSTOMER);

        when(customerRepository.findById(id)).thenReturn(Optional.of(mockCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        ResponseEntity<DtoCustomer> response = customerService.updateCustomer(id.toString(), updatedDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Johnny", response.getBody().getFirstName());
        assertEquals("987654321", response.getBody().getPhoneNumber());
    }

    @Test
    void testDeleteCustomer() {
        String email = "john.doe@example.com";
        UUID id = UUID.randomUUID();
        Customer mockCustomer = new Customer(id, "John", "Doe", email, Role.CUSTOMER, "123456789", "$2a$10$password", LocalDate.now());
        when(customerRepository.findById(id)).thenReturn(Optional.of(mockCustomer));
        when(bookingRepository.findByCustomer_Email(email)).thenReturn(List.of());

        ResponseEntity<String> response = customerService.deleteCustomer(id.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer has been deleted successfully.", response.getBody());
        verify(customerRepository, times(1)).deleteById(mockCustomer.getId());
    }

    @Test
    void testChangePassword() {
        String email = "john.doe@example.com";
        String oldPassword = "oldPassword123";
        String newPassword = "newPassword123";
        String encodedOldPassword = "$2a$10$encodedOldPassword";
        UUID id = UUID.randomUUID();

        Customer mockCustomer = new Customer(id, "John", "Doe", email, Role.CUSTOMER, "123456789", encodedOldPassword, LocalDate.now());

        when(customerRepository.findById(id)).thenReturn(Optional.of(mockCustomer));
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("$2a$10$encodedNewPassword");

        ResponseEntity<String> response = customerService.changePassword(id.toString(), oldPassword, newPassword);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully.", response.getBody());
    }
}
