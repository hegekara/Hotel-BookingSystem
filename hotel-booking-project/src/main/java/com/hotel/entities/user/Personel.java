package com.hotel.entities.user;

import java.time.LocalDate;
import java.util.UUID;

import com.hotel.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Personel extends BaseUser{

    @Column(nullable = true)
    private LocalDate startingDate;

    public Personel(UUID id, String firstName, String lastName, String email, Role role, String phoneNumber, String password, LocalDate startingDate) {
        super(id, firstName, lastName, email, role, phoneNumber, password);
        this.startingDate = startingDate;
    }
}
