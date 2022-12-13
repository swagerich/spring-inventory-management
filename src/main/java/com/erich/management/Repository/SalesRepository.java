package com.erich.management.Repository;

import com.erich.management.Entity.Sales;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SalesRepository extends CrudRepository<Sales, Long> {

    Optional<Sales> findSalesByCode(String code);
}
