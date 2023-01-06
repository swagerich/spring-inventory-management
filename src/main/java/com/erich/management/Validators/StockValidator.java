package com.erich.management.Validators;

import com.erich.management.Dto.StockDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StockValidator {

    public static List<String> validator(StockDto stockDto) {
        List<String> errors = new ArrayList<>();
        if (stockDto == null) {
            errors.add("Por favor complete la fecha del movimiento");
            errors.add("Por favor complete la cantidad del movimiento");
            errors.add("Por favor complete el artículo");
            errors.add("Por favor complete el tipo de movimiento");
            return errors;
        }
        if (stockDto.getDateStock() == null) {
            errors.add("Por favor complete la fecha del movimiento");
        }
        if (stockDto.getQuantity() == null || stockDto.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Por favor complete la cantidad del movimiento");
        }
        if (stockDto.getArticle() == null || stockDto.getArticle().getId() == null) {
            errors.add("Por favor complete el artículo");
        }
        if (!StringUtils.hasLength(stockDto.getTypeStock().name())) {
            errors.add("Por favor complete el tipo de movimiento");
        }
        return errors;
    }
}
