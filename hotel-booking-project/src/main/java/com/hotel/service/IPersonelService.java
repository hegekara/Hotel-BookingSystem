package com.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;

public interface IPersonelService {

    public ResponseEntity<DtoPersonel> registerPersonel(DtoPersonelIU personel);

    public ResponseEntity<DtoResponse> loginPersonel(String email, String password);

    public ResponseEntity<List<DtoPersonel>> getAllPersonels();

    public ResponseEntity<DtoPersonel> getPersonelById(Long id);

    public ResponseEntity<DtoPersonel> updatePersonel(Long id, DtoPersonelIU updatedPersonel);

    public ResponseEntity<String> deletePersonel(Long id);

    public ResponseEntity<String> changePassword(Long id, String oldPassword, String newPassword);
}
