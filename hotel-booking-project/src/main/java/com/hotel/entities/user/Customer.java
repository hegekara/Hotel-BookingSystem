package com.hotel.entities.user;

import java.time.LocalDate;

import com.hotel.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Customer extends BaseUser{

    @Column(nullable = true)
    private LocalDate registerDate;


    public Customer(Long id, String firstName, String lastName, String email, Role role, String phoneNumber, String password, LocalDate registerDate) {
        super(id, firstName, lastName, email, role, phoneNumber, password);
        this.registerDate = registerDate;
    }
}
