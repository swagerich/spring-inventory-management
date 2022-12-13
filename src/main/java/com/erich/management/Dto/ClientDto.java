package com.erich.management.Dto;


import com.erich.management.Entity.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientDto {

    private Long id;

    private String name;

    private String lastName;

    private AddressDto address;

    private String photo;

    private String email;

    private String phone;

    private Long idEntreprise;

    @JsonIgnore
    private List<ClientOrderDto> clientOrder;


    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            return null;
        }
        return ClientDto.builder().id(client.getId())
                .name(client.getName())
                .lastName(client.getLastName())
                .address(AddressDto.fromEntity(client.getAddress()))
                .photo(client.getPhoto())
                .email(client.getEmail())
                .phone(client.getPhone())
                .idEntreprise(client.getIdEntreprise()).build();
    }


    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        return Client.builder().id(clientDto.getId())
                .name(clientDto.getName())
                .lastName(clientDto.getLastName())
                .address(AddressDto.toEntity(clientDto.getAddress()))
                .photo(clientDto.getPhoto())
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .idEntreprise(clientDto.getIdEntreprise()).build();
    }
}
