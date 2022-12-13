package com.erich.management.Repository;

import com.erich.management.Entity.Enterprise;
import org.springframework.data.repository.CrudRepository;

public interface EnterpriseRepository extends CrudRepository<Enterprise,Long> {
}
