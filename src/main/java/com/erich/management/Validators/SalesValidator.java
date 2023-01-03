package com.erich.management.Validators;

import com.erich.management.Dto.SalesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SalesValidator {

    public static List<String> validator(SalesDto salesDto) {

        List<String> errors = new ArrayList<>();
        if (salesDto == null) {
            errors.add("Porfavor complete el codigo de pedido");
            errors.add("Porfavor complete la fecha de orden");
            return errors;
        }
        if (!StringUtils.hasLength(salesDto.getCode())) {
            errors.add("Porfavor complete el codigo de pedido");
        }
        if (salesDto.getSaleDate() == null) {
            errors.add("Porfavor complete la fecha de orden");
        }

        return errors;
    }
}
