package com.erich.management.Dto;

import com.erich.management.Entity.ClientOrder;
import com.erich.management.Utils.Enum.OrdeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ClientOrderDto {

    private Long id;

    private String code;

    private Instant dateOrder;

    private OrdeStatus ordeStatus;

    private Long idEntreprise;

    private ClientDto client;

    private List<ClientOrderLineDto> clientOrderLine;

    public static ClientOrderDto fromEntity(ClientOrder clientOrder) {
        if (clientOrder == null) {
            return null;
        }
        return ClientOrderDto.builder().id(clientOrder.getId())
                .code(clientOrder.getCode())
                .dateOrder(clientOrder.getDateOrder())
                .ordeStatus(clientOrder.getOrdeStatus())
                .idEntreprise(clientOrder.getIdEntreprise())
                .client(ClientDto.fromEntity(clientOrder.getClient())).build();
    }

    public static ClientOrder toEntity(ClientOrderDto clientOrderDto){
        if(clientOrderDto == null){
            return null;
        }
        return ClientOrder.builder().id(clientOrderDto.getId())
                .code(clientOrderDto.getCode())
                .dateOrder(clientOrderDto.getDateOrder())
                .ordeStatus(clientOrderDto.getOrdeStatus())
                .idEntreprise(clientOrderDto.getIdEntreprise())
                .client(ClientDto.toEntity(clientOrderDto.getClient())).build();
    }

    public boolean isOrderDelivered(){
        return OrdeStatus.DELIVERED.equals(this.ordeStatus);
    }
}
