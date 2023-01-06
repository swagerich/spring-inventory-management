package com.erich.management.Dto;

import com.erich.management.Entity.SaleLine;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleLineDto {

    private Long id;

    private SalesDto sale;

    private ArticleDto article;

    private BigDecimal quantity;

    private BigDecimal priceUnitario;

    private Long idEntreprise;

    public static SaleLineDto fromEntity(SaleLine saleLine){
        if(saleLine == null){
            return null;
        }
        return SaleLineDto.builder().id(saleLine.getId())
                .sale(SalesDto.fromEntity(saleLine.getSale()))
                .article(ArticleDto.fromEntity(saleLine.getArticle()))
                .quantity(saleLine.getQuantity())
                .priceUnitario(saleLine.getPriceUnitario())
                .idEntreprise(saleLine.getIdEntreprise()).build();
    }

    public static SaleLine toEntity(SaleLineDto saleLineDto){

        if(saleLineDto == null){
            return null;
        }
        return SaleLine.builder().id(saleLineDto.getId())
                .sale(SalesDto.toEntity(saleLineDto.getSale()))
                .article(ArticleDto.toEntity(saleLineDto.getArticle()))
                .quantity(saleLineDto.getQuantity())
                .priceUnitario(saleLineDto.getPriceUnitario())
                .idEntreprise(saleLineDto.getIdEntreprise()).build();
    }
}
