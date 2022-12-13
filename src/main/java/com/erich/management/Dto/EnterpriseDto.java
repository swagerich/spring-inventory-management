package com.erich.management.Dto;


import com.erich.management.Entity.Enterprise;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EnterpriseDto {

    private Long id;

    private String name;

    private String description;

    private AddressDto address;

    private String taxCode;

    private String photo;

    private String email;

    private String phone;

    private String webSite;

    @JsonIgnore
    private List<UserDto> users;

    public static EnterpriseDto fromEntity(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return EnterpriseDto.builder().id(enterprise.getId())
                .name(enterprise.getName())
                .description(enterprise.getDescription())
                .address(AddressDto.fromEntity(enterprise.getAddress()))
                .taxCode(enterprise.getTaxCode())
                .photo(enterprise.getPhoto())
                .email(enterprise.getEmail())
                .phone(enterprise.getPhone())
                .webSite(enterprise.getWebSite()).build();
    }

    public static Enterprise toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder().id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .description(enterpriseDto.getDescription())
                .address(AddressDto.toEntity(enterpriseDto.getAddress()))
                .taxCode(enterpriseDto.getTaxCode())
                .photo(enterpriseDto.getPhoto())
                .email(enterpriseDto.getEmail())
                .phone(enterpriseDto.getPhone())
                .webSite(enterpriseDto.getWebSite()).build();
    }

}
