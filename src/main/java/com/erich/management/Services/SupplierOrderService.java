package com.erich.management.Services;

import com.erich.management.Dto.SupplierOrderDto;
import com.erich.management.Dto.SupplierOrderLineDto;
import com.erich.management.Entity.SupplierOrderLine;
import com.erich.management.Utils.Enum.OrdeStatus;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierOrderService {

    SupplierOrderDto save(SupplierOrderDto supplierOrderDto);

    SupplierOrderDto updateOrderStatus(Long idOrder, OrdeStatus ordeStatus);

    SupplierOrderDto updateQuantityOrder(Long idOrder, Long idOrderLine, BigDecimal quantity);

    SupplierOrderDto updateSupplier(Long idOrder, Long idSupplier);

    SupplierOrderDto updateArticle(Long idOrder, Long idOrderLine, Long idArticle);

    //    Eliminar artículo ==> eliminar Línea de pedido del proveedor
    SupplierOrderDto deleteArticle(Long idOrder, Long idOrderLine);

    SupplierOrderDto findById(Long id);

    SupplierOrderDto findSupplierByCode(String code);

    List<SupplierOrderDto> findAll();

    List<SupplierOrderLineDto> findAllSupplierOrderLineBySupplierOrderId(Long id);

    void delete(Long id);
}
