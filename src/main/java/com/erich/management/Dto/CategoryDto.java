package com.erich.management.Dto;


import com.erich.management.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CategoryDto {

    private Long id;

    private String code;

    private String description;

    private Long idEntreprise;

    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDto.builder().id(category.getId())
                .code(category.getCode())
                .description(category.getDescription())
                .idEntreprise(category.getIdEntreprise())
                .build();

    }

    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null){
            return null;
        }
        return Category.builder().id(categoryDto.getId()).code(categoryDto.getCode())
                .description(categoryDto.getDescription())
                .idEntreprise(categoryDto.getIdEntreprise())
                .build();
    }


}
