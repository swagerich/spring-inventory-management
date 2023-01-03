package com.erich.management.Dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ChangeThePasswordUserDto {

    private Long id;
    private String password;
    private String confirmPassword;
}
