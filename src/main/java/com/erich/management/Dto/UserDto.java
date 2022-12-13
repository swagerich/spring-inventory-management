package com.erich.management.Dto;

import com.erich.management.Entity.User;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDto {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private Instant dateOfBirth;

    private String photo;

    private EnterpriseDto enterprise;

    private AddressDto address;

    private List<RolesDto> roles;

    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder().id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .photo(user.getPhoto())
                .enterprise(EnterpriseDto.fromEntity(user.getEnterprise()))
                .address(AddressDto.fromEntity(user.getAddress()))
                .roles(user.getRoles() != null ?
                        user.getRoles().stream()
                                .map(roles -> RolesDto.fromEntity(roles)).collect(Collectors.toList()) : null).build();
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder().id(userDto.getId())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .dateOfBirth(userDto.getDateOfBirth())
                .photo(userDto.getPhoto())
                .address(AddressDto.toEntity(userDto.getAddress()))
                .enterprise(EnterpriseDto.toEntity(userDto.getEnterprise())).build();
    }
}
