package com.erich.management.Dto;

import com.erich.management.Entity.ClientOrderLine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientOrderLineDto {

    private Long id;

    private ArticleDto article;

    @JsonIgnore
    private ClientOrderDto clientOrder;

    private BigDecimal quantity;

    private BigDecimal priceUnitario;

    private Long idEntreprise;

    public static ClientOrderLineDto fromEntity(ClientOrderLine clientOrderLine) {
        if (clientOrderLine == null) {
            return null;
        }
        return ClientOrderLineDto.builder().id(clientOrderLine.getId())
                .article(ArticleDto.fromEntity(clientOrderLine.getArticle()))
                .quantity(clientOrderLine.getQuantity())
                .priceUnitario(clientOrderLine.getPriceUnitario())
                .idEntreprise(clientOrderLine.getIdEntreprise()).build();
    }

    public static ClientOrderLine toEntity(ClientOrderLineDto clientOrderLineDto) {
        if (clientOrderLineDto == null) {
            return null;
        }
        return ClientOrderLine.builder()
                .id(clientOrderLineDto.getId())
                .article(ArticleDto.toEntity(clientOrderLineDto.getArticle()))
                .quantity(clientOrderLineDto.getQuantity())
                .priceUnitario(clientOrderLineDto.getPriceUnitario())
                .idEntreprise(clientOrderLineDto.getIdEntreprise())
                .build();

    }
}
