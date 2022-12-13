package com.erich.management.Repository;

import com.erich.management.Entity.SupplierOrderLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierOrderLineRepository extends CrudRepository<SupplierOrderLine,Long> {

    List<SupplierOrderLine> findAllBySupplierOrderId(Long idOrde);

    List<SupplierOrderLine> findAllByArticleId(Long idOrde);

}
