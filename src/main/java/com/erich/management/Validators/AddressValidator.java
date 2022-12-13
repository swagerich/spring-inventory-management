package com.erich.management.Validators;

import com.erich.management.Dto.AddressDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressValidator {

    public static List<String> validator(AddressDto addressDto) {
        List<String> errors = new ArrayList<>();
        if (addressDto == null) {
            errors.add("Por favor complete la dirección 1");
            errors.add("Por favor complete la ciudad");
            errors.add("Por favor complete el país");
            errors.add("Por favor complete el código postal");
            return errors;
        }
        if (!StringUtils.hasLength(addressDto.getAddress1())) {
            errors.add("Por favor complete la dirección 1");
        }
        if (!StringUtils.hasLength(addressDto.getCity())) {
            errors.add("Por favor complete la ciudad");
        }
        if (!StringUtils.hasLength(addressDto.getCountry())) {
            errors.add("Por favor complete el país");
        }
        if (!StringUtils.hasLength(addressDto.getAddress2())) {
            errors.add("Por favor complete la dirección 2");
        }
        return errors;
    }
}
