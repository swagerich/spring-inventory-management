package com.erich.management.Dto;

import com.erich.management.Entity.SupplierOrder;
import com.erich.management.Utils.Enum.OrdeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class SupplierOrderDto {

    private Long id;

    private String code;

    private Instant dateOrder;

    private OrdeStatus ordeStatus;

    private Long idEntreprise;

    private SupplierDto supplier;

    private List<SupplierOrderLineDto> supplierOrderLine;

    public static SupplierOrderDto fromEntity(SupplierOrder supplierOrder) {
        if (supplierOrder == null) {
            return null;
        }
        return SupplierOrderDto.builder().id(supplierOrder.getId())
                .code(supplierOrder.getCode())
                .dateOrder(supplierOrder.getDateOrder())
                .ordeStatus(supplierOrder.getOrdeStatus())
                .idEntreprise(supplierOrder.getIdEntreprise())
                .supplier(SupplierDto.fromEntity(supplierOrder.getSupplier()))
                .build();
    }

    public static SupplierOrder toEntity(SupplierOrderDto supplierOrderDto) {
        if (supplierOrderDto == null) {
            return null;
        }
        return SupplierOrder.builder().id(supplierOrderDto.getId())
                .code(supplierOrderDto.getCode())
                .dateOrder(supplierOrderDto.getDateOrder())
                .ordeStatus(supplierOrderDto.getOrdeStatus())
                .idEntreprise(supplierOrderDto.getIdEntreprise())
                .supplier(SupplierDto.toEntity(supplierOrderDto.getSupplier())).build();

    }

    public boolean isOrderDelivered(){
        return OrdeStatus.DELIVERED.equals(this.ordeStatus);
    }
}
