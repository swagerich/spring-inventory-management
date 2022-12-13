package com.erich.management.Repository;

import com.erich.management.Entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

    Optional<Category> findCategoryByCode(String code);
}
