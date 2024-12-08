package com.hotel.dto.user;

import com.hotel.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPersonel {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;
}
