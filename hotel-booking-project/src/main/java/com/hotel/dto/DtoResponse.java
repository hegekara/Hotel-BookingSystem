package com.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoResponse {
    private Object object;
    private String token;
    private String message = null;
}
