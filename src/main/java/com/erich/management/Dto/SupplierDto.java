package com.erich.management.Dto;

import com.erich.management.Entity.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SupplierDto {

    private Long id;

    private String name;

    private String lastName;

    private String photo;

    private String email;

    private String phone;

    private AddressDto address;

    private Long idEntreprise;

    @JsonIgnore
    private List<SupplierOrderDto> supplierOrder;

    public static SupplierDto fromEntity(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return SupplierDto.builder().id(supplier.getId())
                .name(supplier.getName())
                .lastName(supplier.getLastName())
                .photo(supplier.getPhoto())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(AddressDto.fromEntity(supplier.getAddress()))
                .idEntreprise(supplier.getIdEntreprise()).build();
    }

    public static Supplier toEntity(SupplierDto supplierDto) {
        if (supplierDto == null) {
            return null;
        }
        return Supplier.builder().id(supplierDto.getId())
                .name(supplierDto.getName())
                .lastName(supplierDto.getLastName())
                .photo(supplierDto.getPhoto())
                .email(supplierDto.getEmail())
                .phone(supplierDto.getPhone())
                .address(AddressDto.toEntity(supplierDto.getAddress()))
                .idEntreprise(supplierDto.getIdEntreprise()).build();
    }
}
