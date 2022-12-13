package com.erich.management.Validators;

import com.erich.management.Dto.StockDto;

import java.util.ArrayList;
import java.util.List;

public class StockValidator {

    public List<String> validator(StockDto stockDto){
        List<String> errors = new ArrayList<>();
        if(stockDto == null){
            errors.add("Por favor complete la fecha del movimiento");
            errors.add("Por favor complete la cantidad del movimiento");
            errors.add("Por favor complete el artículo");
            errors.add("Por favor complete el tipo de movimiento");
            return errors;
        }
        if(stockDto.getDateStock() == null){
            errors.add("Por favor complete la fecha del movimiento");
        }
        if(stockDto.getQuantity() == null){
            errors.add("Por favor complete la cantidad del movimiento");
        }
        if(stockDto.getArticle() == null){
            errors.add("Por favor complete el artículo");
        }
        if(stockDto.getTypeStock() == null){
            errors.add("Por favor complete el tipo de movimiento");
        }
        return errors;
    }
}
