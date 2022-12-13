package com.erich.management.Dto;

import com.erich.management.Entity.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolesDto {

    private Long id;

    private String rolesName;

    @JsonIgnore
    private UserDto user;

    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return RolesDto.builder().id(roles.getId())
                .rolesName(roles.getRolesName()).build();
    }

    public static Roles toEntity(RolesDto rolesDto) {
        if (rolesDto == null) {
            return null;
        }
        return Roles.builder().id(rolesDto.getId())
                .rolesName(rolesDto.getRolesName())
                .user(UserDto.toEntity(rolesDto.getUser())).build();
    }
}
