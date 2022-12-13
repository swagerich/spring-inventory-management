package com.erich.management.Repository;

import com.erich.management.Entity.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier,Long> {
}
