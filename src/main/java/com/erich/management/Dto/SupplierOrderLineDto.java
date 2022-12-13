package com.erich.management.Dto;

import com.erich.management.Entity.SupplierOrderLine;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SupplierOrderLineDto {

    private Long id;

    private ArticleDto article;

    private SupplierOrderDto supplierOrder;

    private BigDecimal quantity;

    private BigDecimal priceUnitario;

    private Long idEntreprise;

    public static SupplierOrderLineDto fromEntity(SupplierOrderLine supplierOrderLine) {
        if (supplierOrderLine == null) {
            return null;
        }
        return SupplierOrderLineDto.builder().id(supplierOrderLine.getId())
                .article(ArticleDto.fromEntity(supplierOrderLine.getArticle()))
                .supplierOrder(SupplierOrderDto.fromEntity(supplierOrderLine.getSupplierOrder()))
                .quantity(supplierOrderLine.getQuantity())
                .priceUnitario(supplierOrderLine.getPriceUnitario())
                .idEntreprise(supplierOrderLine.getIdEntreprise()).build();
    }

    public static SupplierOrderLine toEntity(SupplierOrderLineDto supplierOrderLineDto) {
        if (supplierOrderLineDto == null) {
            return null;
        }

        return SupplierOrderLine.builder().id(supplierOrderLineDto.getId())
                .article(ArticleDto.toEntity(supplierOrderLineDto.getArticle()))
                .supplierOrder(SupplierOrderDto.toEntity(supplierOrderLineDto.getSupplierOrder()))
                .quantity(supplierOrderLineDto.getQuantity())
                .priceUnitario(supplierOrderLineDto.getPriceUnitario())
                .idEntreprise(supplierOrderLineDto.getIdEntreprise()).build();
    }
}
