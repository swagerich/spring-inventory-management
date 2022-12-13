package com.erich.management.Repository;

import com.erich.management.Entity.SaleLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SaleLineRepository extends CrudRepository<SaleLine,Long> {

    List<SaleLine> findAllByArticleId(Long id);

    List<SaleLine> findAllBySaleId(Long id);

}
