package com.erich.management.Services;

import com.erich.management.Dto.ClientOrderDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Utils.Enum.OrdeStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ClientOrderService {

    ClientOrderDto save(ClientOrderDto clientOrderDto);

    ClientOrderDto updateOrdeStatus(Long idOrder, OrdeStatus ordeStatus);

    ClientOrderDto updateQuantityOrde(Long idOrder, Long idLineOrder, BigDecimal quantity);

    ClientOrderDto updateClient(Long idOrder,Long idClient);

    ClientOrderDto updateArticle(Long idOrder,Long idLineOrder,Long idArticle);

    //Delete article ==> delete LigneCommandeClient
    ClientOrderDto deleteArticle(Long idOrder,Long idOrderLine);
    ClientOrderDto findById(Long id);

    ClientOrderDto findClientByCode(String code);

    List<ClientOrderDto> findAll();

    List<ClientOrderLineDto> findAllClientOrderLineByClientOrderId(Long idOrder);
    void delete(Long id);
}
