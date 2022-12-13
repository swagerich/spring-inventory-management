package com.erich.management.Dto;

import com.erich.management.Entity.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private String address1;

    private String address2;

    private String city;

    private String codePostal;

    private String country;

    public static AddressDto fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        return AddressDto.builder().address1(address.getAddress1())
                .address2(address.getAddress2())
                .city(address.getCity())
                .codePostal(address.getCodePostal())
                .country(address.getCountry()).build();
    }

    public static Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        return Address.builder().address1(addressDto.getAddress1())
                .address2(addressDto.getAddress2())
                .city(addressDto.getCity())
                .codePostal(addressDto.getCodePostal())
                .country(addressDto.getCountry()).build();
    }
}
