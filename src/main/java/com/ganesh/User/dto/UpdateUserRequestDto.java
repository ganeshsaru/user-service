package com.ganesh.User.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String name;
    private String email;
    private String address;
}
