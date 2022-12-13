package com.erich.management.Validators;

import com.erich.management.Dto.EnterpriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseValidator {

    public static List<String> validator(EnterpriseDto enterpriseDto) {
        List<String> errors = new ArrayList<>();
        if (enterpriseDto == null) {
            errors.add("Por favor ingrese el nombre de la empresa");
            errors.add("Por favor complete la descripción de la empresa");
            errors.add("Por favor, rellene el código fiscal de la empresa");
            errors.add("Por favor, rellene el correo electrónico de la empresa");
            errors.add("Por favor complete el número de teléfono de la empresa");
            errors.addAll(AddressValidator.validator(null));
            return errors;
        }

        if (!StringUtils.hasLength(enterpriseDto.getName())) {
            errors.add("Por favor ingrese el nombre de la empresa");
        }
        if (!StringUtils.hasLength(enterpriseDto.getDescription())) {
            errors.add("Por favor complete la descripción de la empresa");
        }
        if (!StringUtils.hasLength(enterpriseDto.getTaxCode())) {
            errors.add("Por favor, rellene el código fiscal de la empresa");
        }
        if (!StringUtils.hasLength(enterpriseDto.getEmail())) {
            errors.add("Por favor, rellene el correo electrónico de la empresa");
        }
        if (!StringUtils.hasLength(enterpriseDto.getPhone())) {
            errors.add("Por favor complete el número de teléfono de la empresa");
        }

        errors.addAll(AddressValidator.validator(enterpriseDto.getAddress()));
        return errors;
    }
}
