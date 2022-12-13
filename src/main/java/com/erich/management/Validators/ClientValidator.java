package com.erich.management.Validators;

import com.erich.management.Dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static List<String> validator(ClientDto clientDto) {

        List<String> errors = new ArrayList<>();

        if (clientDto == null) {
            errors.add("Porfavor complete el nombre del cliente");
            errors.add("Porfavor complete el nombre completo");
            errors.add("Porfavor complete el correo electronico");
            errors.add("Porfavor complete el numero de telefono");
            return errors;
        }
        if (!StringUtils.hasLength(clientDto.getName())) {
            errors.add("Porfavor complete el nombre del cliente");
        }
        if (!StringUtils.hasLength(clientDto.getLastName())) {
            errors.add("Porfavor complete el nombre completo");
        }
        if (!StringUtils.hasLength(clientDto.getEmail())) {
            errors.add("Porfavor complete el correo electronico");
        }
        if (!StringUtils.hasLength(clientDto.getPhone())) {
            errors.add("Porfavor complete el numero de telefono");
        }
        return errors;
    }
}
