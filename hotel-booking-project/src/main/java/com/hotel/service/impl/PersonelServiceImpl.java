package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;
import com.hotel.entities.user.Personel;
import com.hotel.repository.IPersonelRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.IPersonelService;

@Service
public class PersonelServiceImpl implements IPersonelService{

    @Autowired
    private IPersonelRepository personelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<DtoPersonel> registerPersonel(DtoPersonelIU dtoPersonelIU) {

        try {
            String encryptedPassword = passwordEncoder.encode(dtoPersonelIU.getPassword());
            dtoPersonelIU.setPassword(encryptedPassword);
    
            Personel personel = new Personel();
    
            BeanUtils.copyProperties(dtoPersonelIU, personel);
            personel.setStartingDate(LocalDate.now());
            personelRepository.save(personel);
    
            DtoPersonel dtoPersonel = new DtoPersonel();
            BeanUtils.copyProperties(personel, dtoPersonel);
    
            return ResponseEntity.ok().body(dtoPersonel);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @Override
    public ResponseEntity<DtoResponse> loginPersonel(String email, String password) {
        Personel personel = personelRepository.findByEmail(email);
        if (personel != null) {
            if (passwordEncoder.matches(password, personel.getPassword())) {
                DtoPersonel dtoPersonel = new DtoPersonel();
                BeanUtils.copyProperties(personel, dtoPersonel);
    
                String token = jwtUtil.generateToken(email, personel.getRole());
                return ResponseEntity.ok(new DtoResponse(dtoPersonel, token, "login successful"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DtoResponse(null, null, "Invalid credentials"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DtoResponse(null, null, "Person not found"));
    }


    @Override
    public ResponseEntity<List<DtoPersonel>> getAllPersonels() {
        List<Personel> personelList = personelRepository.findAll();

        {
            List<DtoPersonel> dtoPersonelList = new ArrayList<>();
            for (Personel item : personelList) {
                DtoPersonel dtoPersonel = new DtoPersonel();
                BeanUtils.copyProperties(item, dtoPersonel);
                dtoPersonelList.add(dtoPersonel);
            }

            return ResponseEntity.ok().body(dtoPersonelList);
        }
    }


    @Override
    public ResponseEntity<DtoPersonel> getPersonelByEmail(String email) {
        Personel personel = personelRepository.findByEmail(email);
        
        if(personel != null){
            DtoPersonel dtoPersonel = new DtoPersonel();

            BeanUtils.copyProperties(personel, dtoPersonel);
            return ResponseEntity.ok().body(dtoPersonel);
        }
        return ResponseEntity.badRequest().body(null);
    }


    @Override
    public ResponseEntity<DtoPersonel> updatePersonel(String email, DtoPersonelIU updatedPersonel) {
        Personel personel = personelRepository.findByEmail(email);
    
        if (personel != null) {

            personel.setFirstName(updatedPersonel.getFirstName());
            personel.setLastName(updatedPersonel.getLastName());
            personel.setEmail(updatedPersonel.getEmail());
            personel.setPhoneNumber(updatedPersonel.getPhoneNumber());

            Personel updatedEntity = personelRepository.save(personel);

            DtoPersonel dtoPersonel = new DtoPersonel();
            BeanUtils.copyProperties(updatedEntity, dtoPersonel);
    
            return ResponseEntity.ok(dtoPersonel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    @Override
    public ResponseEntity<String> deletePersonel(String email) {
        Personel personel = personelRepository.findByEmail(email);
    
        if (personel != null) {
            personelRepository.delete(personel);
            return ResponseEntity.ok("Personel has been deleted successfully.");
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personel not found.");
    }


    @Override
    public ResponseEntity<String> changePassword(String email, String oldPassword, String newPassword) {
        Personel personel = personelRepository.findByEmail(email);
    
        if (personel != null) {
    
            if (passwordEncoder.matches(oldPassword, personel.getPassword())) {
                personel.setPassword(passwordEncoder.encode(newPassword));
                personelRepository.save(personel);
    
                return ResponseEntity.ok("Password updated successfully.");
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password does not match.");
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personel not found.");
        }
    }
}
