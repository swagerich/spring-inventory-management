package com.erich.management.Repository;

import com.erich.management.Entity.ClientOrder;
import com.erich.management.Entity.SupplierOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierOrderRepository extends CrudRepository<SupplierOrder, Long> {

    Optional<SupplierOrder> findSupplierOrderByCode(String code);

    List<ClientOrder> findAllBySupplierId(Long id);
}
