package com.erich.management.Dto;

import com.erich.management.Entity.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDto {

    private Long id;

    private String codeArticle;

    private String description;

    private BigDecimal priceUnitario;

    private BigDecimal tax;

    private BigDecimal priceUnitarioTtc;

    private String photo;

    private Long idEntreprise;

    private CategoryDto category;


    public static ArticleDto fromEntity(Article article) {
        if (article == null) {
            return null;
        }
        return ArticleDto.builder().id(article.getId())
                .codeArticle(article.getCodeArticle())
                .description(article.getDescription())
                .priceUnitario(article.getPriceUnitario())
                .tax(article.getTax())
                .priceUnitarioTtc(article.getPriceUnitarioTtc())
                .photo(article.getPhoto())
                .idEntreprise(article.getIdEntreprise())
                .category(CategoryDto.fromEntity(article.getCategory())).build();
    }

    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }
        return Article.builder().id(articleDto.getId())
                .codeArticle(articleDto.getCodeArticle())
                .description(articleDto.getDescription())
                .priceUnitario(articleDto.getPriceUnitario())
                .tax(articleDto.getTax())
                .priceUnitarioTtc(articleDto.getPriceUnitarioTtc())
                .photo(articleDto.getPhoto())
                .idEntreprise(articleDto.getIdEntreprise())
                .category(CategoryDto.toEntity(articleDto.getCategory())).build();
    }
}
