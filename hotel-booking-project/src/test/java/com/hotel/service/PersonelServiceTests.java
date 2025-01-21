package com.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotel.constants.Role;
import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;
import com.hotel.entities.user.Personel;
import com.hotel.repository.IPersonelRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.impl.PersonelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonelServiceTests {

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private IPersonelRepository personelRepository;

    @InjectMocks
    private PersonelServiceImpl personelService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;



    @Test
    void testRegisterPersonel() {
        DtoPersonelIU mockPersonelIU = new DtoPersonelIU(
            "Aaa",
            "Bbb", 
            "cc@ccc.com", 
            "4838493", 
            "382ds*1", 
            Role.ADMIN
        );

        Personel savedPersonel = new Personel(
            null,
            "Aaa",
            "Bbb",
            "cc@ccc.com",
            Role.ADMIN,
            "4838493",
            "encryptedPassword",
            LocalDate.of(2024, 12, 11)
        );

        DtoPersonel expectedResponse = new DtoPersonel(
            null,
            "Aaa", 
            "Bbb", 
            "cc@ccc.com", 
            "4838493", 
            Role.ADMIN
        );

        when(personelRepository.save(any(Personel.class))).thenReturn(savedPersonel);

        ResponseEntity<DtoPersonel> result = personelService.registerPersonel(mockPersonelIU);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }


    @Test
    void testLoginPersonel() {
        UUID id = UUID.randomUUID();
        List<Personel> mockPersonelList = Arrays.asList(
            new Personel(id, "Aaa", "Bbb", "cc@ccc.com", Role.ADMIN, "4838493", "$2a$10$encodedPassword1", LocalDate.parse("2024-01-01")),
            new Personel(UUID.randomUUID(), "Xxx", "Yyy", "xx@yyy.com", Role.MANAGER, "1234567", "$2a$10$encodedPassword2", LocalDate.parse("2024-02-01")),
            new Personel(UUID.randomUUID(), "Jjj", "Kkk", "jj@kkk.com", Role.PERSONEL, "9876543", "$2a$10$encodedPassword3", LocalDate.parse("2024-03-01"))
        );
    
        String email = "cc@ccc.com";
        String password = "382ds*1";
    
        when(personelRepository.findByEmail(email)).thenReturn(mockPersonelList.get(0));
    
        when(passwordEncoder.matches(password, mockPersonelList.get(0).getPassword())).thenReturn(true);
    
        DtoPersonel expectedDtoPersonel = new DtoPersonel(id, "Aaa", "Bbb", "cc@ccc.com", "4838493", Role.ADMIN);
        String expectedToken = "mockedJWTToken";
        when(jwtUtil.generateToken(email, Role.ADMIN)).thenReturn(expectedToken);
        DtoResponse expectedResponse = new DtoResponse(expectedDtoPersonel, expectedToken, "login successful");
    
        ResponseEntity<DtoResponse> result = personelService.loginPersonel(email, password);
    
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse.getMessage(), result.getBody().getMessage());
        assertEquals(expectedResponse.getToken(), result.getBody().getToken());
        assertEquals(expectedResponse.getObject(), result.getBody().getObject());
    }


    @Test
    void testGetAllPersonel() {
        List<Personel> mockPersonelList = Arrays.asList(
            new Personel(UUID.randomUUID(), "Aaa", "Bbb", "cc@ccc.com", Role.ADMIN, "4838493", "382ds*1", LocalDate.parse("2024-01-01")),
            new Personel(UUID.randomUUID(), "Xxx", "Yyy", "xx@yyy.com", Role.MANAGER, "1234567", "password1", LocalDate.parse("2024-02-01")),
            new Personel(UUID.randomUUID(), "Jjj", "Kkk", "jj@kkk.com", Role.PERSONEL, "9876543", "mod@123", LocalDate.parse("2024-03-01"))
        );

        when(personelRepository.findAll()).thenReturn(mockPersonelList);

        ResponseEntity<List<DtoPersonel>> result = personelService.getAllPersonels();

        assertNotNull(result);
        assertEquals(3, result.getBody().size());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(personelRepository, times(1)).findAll();
    }


    @Test
    void testUpdatePersonel() {
        UUID id = UUID.randomUUID();

        Personel existingPersonel = new Personel(
            id,
            "Aaa",
            "Bbb",
            "cc@ccc.com",
            Role.ADMIN,
            "4838493",
            "$2a$10$encodedPassword1",
            LocalDate.parse("2024-01-01")
        );
    
        DtoPersonelIU updatedPersonelDTO = new DtoPersonelIU(
            "Ege",
            "Kara",
            "cc@ccc.com",
            "4838493",
            "$2a$10$encodedPassword1",
            Role.ADMIN
        );
    
        Personel updatedPersonel = new Personel(
            id,
            "Ege",
            "Kara",
            "cc@ccc.com",
            Role.ADMIN,
            "4838493",
            "$2a$10$encodedPassword1",
            LocalDate.parse("2024-01-01")
        );
    
        DtoPersonel expectedResponse = new DtoPersonel(
            id,
            "Ege",
            "Kara",
            "cc@ccc.com",
            "4838493",
            Role.ADMIN
        );
    
        when(personelRepository.findById(id)).thenReturn(Optional.of(existingPersonel));
        when(personelRepository.save(any(Personel.class))).thenReturn(updatedPersonel);
    
        ResponseEntity<DtoPersonel> result = personelService.updatePersonel(id.toString(), updatedPersonelDTO);
    
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }


    @Test
    void testDeletePersonel_Success() {
        String email = "cc@ccc.com";
        UUID id = UUID.randomUUID();
        Personel mockPersonel = new Personel(
            id,
            "Aaa",
            "Bbb",
            email,
            Role.ADMIN,
            "4838493",
            "$2a$10$encodedPassword1",
            LocalDate.parse("2024-01-01")
        );

        when(personelRepository.findById(id)).thenReturn(Optional.of(mockPersonel));

        ResponseEntity<String> response = personelService.deletePersonel(id.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Personel has been deleted successfully.", response.getBody());

        verify(personelRepository, times(1)).delete(mockPersonel);
    }

    @Test
    void testDeletePersonel_NotFound() {
        UUID id = UUID.randomUUID();
        when(personelRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = personelService.deletePersonel(id.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
        assertEquals("Personel not found.", response.getBody());

        verify(personelRepository, times(0)).delete(any(Personel.class));
    }



    @Test
    void testChangePassword_Success() {
        String email = "cc@ccc.com";
        String oldPassword = "oldPassword123";
        String newPassword = "newPassword123";
        String encodedOldPassword = "$2a$10$encodedOldPassword";
        UUID id = UUID.randomUUID();

        Personel mockPersonel = new Personel(
            id,
            "Aaa",
            "Bbb",
            email,
            Role.ADMIN,
            "4838493",
            encodedOldPassword,
            LocalDate.parse("2024-01-01")
        );

        when(personelRepository.findById(id)).thenReturn(Optional.of(mockPersonel));
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("$2a$10$encodedNewPassword");

        ResponseEntity<String> response = personelService.changePassword(id.toString(), oldPassword, newPassword);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully.", response.getBody());

        verify(personelRepository, times(1)).save(mockPersonel);
    }

    @Test
    void testChangePassword_OldPasswordMismatch() {
        String email = "cc@ccc.com";
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword123";
        String encodedOldPassword = "$2a$10$encodedOldPassword";
        UUID id = UUID.randomUUID();

        Personel mockPersonel = new Personel(
            id,
            "Aaa",
            "Bbb",
            email,
            Role.ADMIN,
            "4838493",
            encodedOldPassword,
            LocalDate.parse("2024-01-01")
        );

        when(personelRepository.findById(id)).thenReturn(Optional.of(mockPersonel));
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(false);

        ResponseEntity<String> response = personelService.changePassword(id.toString(), oldPassword, newPassword);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Old password does not match.", response.getBody());

        verify(personelRepository, times(0)).save(any(Personel.class));
    }

    @Test
    void testChangePassword_PersonelNotFound() {
        String oldPassword = "oldPassword123";
        String newPassword = "newPassword123";
        UUID id = UUID.randomUUID();

        when(personelRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = personelService.changePassword(id.toString(), oldPassword, newPassword);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Personel not found.", response.getBody());

        verify(personelRepository, times(0)).save(any(Personel.class));
    }
}
