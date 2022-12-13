package com.erich.management.Repository;

import com.erich.management.Entity.ClientOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientOrderRepository extends CrudRepository<ClientOrder,Long> {

    Optional<ClientOrder> findClientOrderByCode(String code);

    List<ClientOrder> findAllByClientId(Long id);

}
