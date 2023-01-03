package com.erich.management.Validators;

import com.erich.management.Dto.ClientDto;
import com.erich.management.Dto.SupplierDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SupplierValidator {

    public static List<String> validator(SupplierDto supplierDto) {

        List<String> errors = new ArrayList<>();

        if (supplierDto == null) {
            errors.add("Porfavor complete el nombre del proveedor");
            errors.add("Porfavor complete el nombre completo del proveedor");
            errors.add("Porfavor complete el correo electronico del proveedor");
            errors.add("Porfavor complete el numero de telefono del proveedor");
            return errors;
        }
        if (!StringUtils.hasLength(supplierDto.getName())) {
            errors.add("Porfavor complete el nombre del proveedor");
        }
        if (!StringUtils.hasLength(supplierDto.getLastName())) {
            errors.add("Porfavor complete el nombre completo del proveedor");
        }
        if (!StringUtils.hasLength(supplierDto.getEmail())) {
            errors.add("Porfavor complete el correo electronico del proveedor");
        }
        if (!StringUtils.hasLength(supplierDto.getPhone())) {
            errors.add("Porfavor complete el numero de telefono del proveedor");
        }
        return errors;
    }
}
