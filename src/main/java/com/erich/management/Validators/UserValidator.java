package com.erich.management.Validators;

import com.erich.management.Dto.UserDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validate(UserDto userDto) {

        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("Porfavor complete el nombre del usuario!");
            errors.add("Porfavor complete el nombre completo!");
            errors.add("Porfavor complete el correo!");
            errors.add("Porfavor complete la direccion de usuario");
            return errors;
        }

        if (!StringUtils.hasLength(userDto.getName())) {
            errors.add("Porfavor complete el nombre del usuario");
        }
        if (!StringUtils.hasLength(userDto.getLastName())) {
            errors.add("Porfavor complete el nombre completo!");
        }
        if (!StringUtils.hasLength(userDto.getEmail())) {
            errors.add("Porfavor complete el correo electronico");
        }
        if (!StringUtils.hasLength(userDto.getPassword())) {
            errors.add("Porfavor complete la contraseña");
        }
        if (userDto.getDateOfBirth() == null) {
            errors.add("Porfavor complete la fecha de su cumpleaños");
        }
        if (userDto.getAddress() == null) {
            errors.add("Porfavor complete la direccion de usuario");
        } else {
            if (!StringUtils.hasLength(userDto.getAddress().getAddress1())) {
                errors.add("El Campo direccion 1 es obligatorio");
            }
            if (!StringUtils.hasLength(userDto.getAddress().getAddress2())) {
                errors.add("El Campo direccion 2 es obligatorio");
            }
            if (!StringUtils.hasLength(userDto.getAddress().getCity())) {
                errors.add("El Campo ciudad es obligatorio");
            }
            if (!StringUtils.hasLength(userDto.getAddress().getCodePostal())) {
                errors.add("El Campo codigo postal es obligatorio");
            }
            if (!StringUtils.hasLength(userDto.getAddress().getCountry())) {
                errors.add("El Campo pais es obligatorio");
            }
        }

        return errors;
    }
}
