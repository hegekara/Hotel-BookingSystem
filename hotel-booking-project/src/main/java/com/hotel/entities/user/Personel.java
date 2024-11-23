package com.hotel.entities.user;

import java.time.LocalDate;

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
}
