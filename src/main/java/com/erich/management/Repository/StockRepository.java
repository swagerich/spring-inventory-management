package com.erich.management.Repository;

import com.erich.management.Entity.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {

    @Query("select sum(s.quantity) from Stock s where  s.article.id = :idArticle")
    BigDecimal stockRealArticle(@Param("idArticle") Long idArticle);

    List<Stock> findAllByArticleId(Long idArticle);
}
