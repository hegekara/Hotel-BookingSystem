package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.constants.Role;
import com.hotel.dto.DtoPersonel;
import com.hotel.dto.DtoPersonelIU;
import com.hotel.dto.DtoResponse;
import com.hotel.entities.Personel;
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
            personel.setRole(Role.PERSONEL);
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
        if(personel != null){
            if(passwordEncoder.matches(password, personel.getPassword())){
                DtoPersonel dtoPersonel = new DtoPersonel();
                BeanUtils.copyProperties(personel, dtoPersonel);
                
                String token = jwtUtil.generateToken(email, personel.getRole());
                return ResponseEntity.ok(new DtoResponse(dtoPersonel,"Bearer " + token, "login succsessful"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
    public ResponseEntity<DtoPersonel> getPersonelById(Long id) {
        Optional<Personel> optional = personelRepository.findById(id);
        
        if(optional.isPresent()){
            Personel personel = optional.get();
            DtoPersonel dtoPersonel = new DtoPersonel();

            BeanUtils.copyProperties(personel, dtoPersonel);
            return ResponseEntity.ok().body(dtoPersonel);
        }
        return ResponseEntity.badRequest().body(null);
    }


    @Override
    public ResponseEntity<DtoPersonel> updatePersonel(Long id, DtoPersonelIU updatedPersonel) {
        Optional<Personel> existingPersonel = personelRepository.findById(id);
    
        if (existingPersonel.isPresent()) {
            Personel personel = existingPersonel.get();

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
    public ResponseEntity<String> deletePersonel(Long id) {
        Optional<Personel> existingPersonel = personelRepository.findById(id);
    
        if (existingPersonel.isPresent()) {
            personelRepository.deleteById(id);
            return ResponseEntity.ok("Personel with ID " + id + " has been deleted successfully.");
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personel with ID " + id + " not found.");
    }


    @Override
    public ResponseEntity<String> changePassword(Long id, String oldPassword, String newPassword) {
        Optional<Personel> existingPersonel = personelRepository.findById(id);
    
        if (existingPersonel.isPresent()) {
            Personel personel = existingPersonel.get();
    
            if (passwordEncoder.matches(oldPassword, personel.getPassword())) {
                personel.setPassword(passwordEncoder.encode(newPassword));
                personelRepository.save(personel);
    
                return ResponseEntity.ok("Password updated successfully.");
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password does not match.");
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personel with ID " + id + " not found.");
        }
    }
}
