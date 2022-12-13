package com.erich.management.Dto;

import com.erich.management.Entity.Sales;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class SalesDto {

    private Long id;

    private String code;

    private Instant saleDate;

    private String comments;

    private Long idEntreprise;

    private List<SaleLineDto> saleLines;

    public static SalesDto fromEntity(Sales sales) {
        if (sales == null) {
            return null;
        }
        return SalesDto.builder().id(sales.getId())
                .code(sales.getCode())
                .comments(sales.getComments())
                .idEntreprise(sales.getIdEntreprise()).build();
    }

    public static Sales toEntity(SalesDto salesDto) {
        if (salesDto == null) {
            return null;
        }
        return Sales.builder().id(salesDto.getId())
                .code(salesDto.getCode())
                .comments(salesDto.getComments())
                .idEntreprise(salesDto.getIdEntreprise()).build();
    }
}
