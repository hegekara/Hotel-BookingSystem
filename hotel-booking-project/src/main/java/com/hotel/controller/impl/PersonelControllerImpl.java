package com.hotel.controller.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.hotel.constants.Role;
import com.hotel.controller.IPersonelController;
import com.hotel.dto.DtoResponse;
import com.hotel.dto.user.DtoPersonel;
import com.hotel.dto.user.DtoPersonelIU;
import com.hotel.service.IPersonelService;

@RestController
@RequestMapping("rest/api/personel")
public class PersonelControllerImpl implements IPersonelController{

    @Autowired
    private IPersonelService personelService;

    @PostMapping("/register")
    public ResponseEntity<DtoPersonel> registerPersonel(@RequestBody DtoPersonelIU personel) {
        return personelService.registerPersonel(personel);
    }

    @PostMapping("/login")
    public ResponseEntity<DtoResponse> loginPersonel(@RequestParam(required = true) String email, @RequestParam(required = true) String password){
        return personelService.loginPersonel(email, password);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoPersonel>> getAllPersonel() {
        return personelService.getAllPersonels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoPersonel> getPersonelById(@PathVariable(required = true) String id) {
        return personelService.getPersonelById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoPersonel> updatePersonel(
            @PathVariable(required = true) String id,
            @RequestBody(required = true) DtoPersonelIU updatedPersonel) {
        return personelService.updatePersonel(id, updatedPersonel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePersonel(@PathVariable String id) {
        return personelService.deletePersonel(id);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable String id,
            @RequestParam(name = "oldPassword", required = true) String oldPassword,
            @RequestParam(name = "newPassword", required = true) String newPassword) {
        return personelService.changePassword(id, oldPassword, newPassword);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        List<String> roles = Arrays.stream(Role.values())
                                    .map(Enum::name)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}
