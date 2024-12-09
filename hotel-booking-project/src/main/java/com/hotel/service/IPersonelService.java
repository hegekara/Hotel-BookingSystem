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

    public ResponseEntity<DtoPersonel> getPersonelByEmail(String email);

    public ResponseEntity<DtoPersonel> updatePersonel(String email, DtoPersonelIU updatedPersonel);

    public ResponseEntity<String> deletePersonel(String email);

    public ResponseEntity<String> changePassword(String email, String oldPassword, String newPassword);
}
