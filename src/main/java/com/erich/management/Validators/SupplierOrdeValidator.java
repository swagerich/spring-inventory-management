package com.erich.management.Validators;

import com.erich.management.Dto.SupplierOrderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SupplierOrdeValidator {

    public static List<String> validate(SupplierOrderDto supplierOrderDto) {
        List<String> errors = new ArrayList<>();
        if (supplierOrderDto == null) {
            errors.add("Por favor, introduzca el código de pedido");
            errors.add("Por favor complete la fecha de la orden");
            errors.add("Por favor complete el estado del pedido");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        if (!StringUtils.hasLength(supplierOrderDto.getCode())) {
            errors.add("Por favor, introduzca el código de pedido");
        }
        if (supplierOrderDto.getDateOrder() == null) {
            errors.add("Por favor complete la fecha de la orden");
        }
        if (!StringUtils.hasLength(supplierOrderDto.getOrdeStatus().toString())) {
            errors.add("Por favor complete el estado del pedido");
        }
        if (supplierOrderDto.getSupplier() == null || supplierOrderDto.getSupplier().getId() == null) {
            errors.add("Por favor, informe al proveedor");
        }
        return errors;
    }
}
