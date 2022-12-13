package com.erich.management.Dto;

import com.erich.management.Entity.Stock;
import com.erich.management.Utils.Enum.SourceStock;
import com.erich.management.Utils.Enum.TypeStock;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class StockDto {

    private Long id;

    private Instant dateStock;

    private BigDecimal quantity;

    private ArticleDto article;

    private TypeStock typeStock;

    private SourceStock sourceStock;

    private Long idEntreprise;

    public static StockDto fromEntity(Stock stock) {
        if (stock == null) {
            return null;
        }
        return StockDto.builder().id(stock.getId())
                .quantity(stock.getQuantity())
                .article(ArticleDto.fromEntity(stock.getArticle()))
                .typeStock(stock.getTypeStock())
                .sourceStock(stock.getSourceStock())
                .idEntreprise(stock.getIdEntreprise()).build();
    }

    public static Stock toEntity(StockDto stockDto) {
        if (stockDto == null) {
            return null;
        }
        return Stock.builder().id(stockDto.getId())
                .quantity(stockDto.getQuantity())
                .article(ArticleDto.toEntity(stockDto.getArticle()))
                .typeStock(stockDto.getTypeStock())
                .sourceStock(stockDto.getSourceStock())
                .idEntreprise(stockDto.getIdEntreprise()).build();
    }
}
