package com.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoEmptyList {
    private Object object;
    private String message = "Liste boş";
}
