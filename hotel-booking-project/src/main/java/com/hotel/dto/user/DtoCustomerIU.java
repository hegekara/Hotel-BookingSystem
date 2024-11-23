package com.hotel.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCustomerIU {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;
}
