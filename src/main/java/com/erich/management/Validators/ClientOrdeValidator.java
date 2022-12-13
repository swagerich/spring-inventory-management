package com.erich.management.Validators;

import com.erich.management.Dto.ClientOrderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientOrdeValidator {

    public static List<String> validate(ClientOrderDto clientOrderDto) {
        List<String> errors = new ArrayList<>();
        if (clientOrderDto == null) {
            errors.add("Por favor, introduzca el código de pedido");
            errors.add("Por favor complete la fecha de la orden");
            errors.add("Por favor complete el estado del pedido");
            errors.add("Por favor, informe al cliente");
            return errors;
        }

        if (!StringUtils.hasLength(clientOrderDto.getCode())) {
            errors.add("Por favor, introduzca el código de pedido");
        }
        if (clientOrderDto.getDateOrder() == null) {
            errors.add("Por favor complete la fecha de la orden");
        }
        if (!StringUtils.hasLength(clientOrderDto.getOrdeStatus().toString())) {
            errors.add("Por favor complete el estado del pedido");
        }
        if (clientOrderDto.getClient() == null || clientOrderDto.getClient().getId() == null) {
            errors.add("Por favor, informe al cliente");
        }

        return errors;
    }
}
