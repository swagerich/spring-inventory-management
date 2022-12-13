package com.erich.management.Repository;

import com.erich.management.Entity.ClientOrderLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientOrderLineRepository extends CrudRepository<ClientOrderLine,Long> {

    List<ClientOrderLine> findAllByClientOrderId(Long id);

    List<ClientOrderLine> findAllByArticleId(Long id);
}
