package com.erich.management.Validators;

import com.erich.management.Dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validate(CategoryDto categoryDto){
        List<String> errors = new ArrayList<>();
        if(categoryDto == null || !StringUtils.hasLength(categoryDto.getCode())){
            errors.add("por favor complete el código de categoría");
        }

        return errors;
    }
}
