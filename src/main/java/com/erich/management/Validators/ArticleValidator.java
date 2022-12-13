package com.erich.management.Validators;

import com.erich.management.Dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto articleDto) {

        List<String> errors = new ArrayList<>();

        if(articleDto == null){
            errors.add("Porfavor complete el  codigo del articulo");
            errors.add("Porfavor complete la descripcion");
            errors.add("Porfavor complete el precio unitario");
            errors.add("Porfavor complete el impuesto");
            errors.add("Porfavor complete el precio unitario Ttc");
            errors.add("Porfavor seleccionar una categoria");
            return errors;
        }

        if (!StringUtils.hasLength(articleDto.getCodeArticle())) {
            errors.add("Porfavor complete el  codigo del articulo");
        }
        if (!StringUtils.hasLength(articleDto.getDescription())) {
            errors.add("Porfavor complete la descripcion");
        }
        if (articleDto.getPriceUnitario() == null) {
            errors.add("Porfavor complete el precio unitario");
        }
        if (articleDto.getTax() == null) {
            errors.add("Porfavor complete el impuesto");
        }
        if (articleDto.getPriceUnitarioTtc() == null) {
            errors.add("Porfavor complete el precio unitario Ttc");
        }
        if (articleDto.getCategory() == null) {
            errors.add("Porfavor seleccionar una categoria");
        }
        return errors;
    }
}
