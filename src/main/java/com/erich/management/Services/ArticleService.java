package com.erich.management.Services;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Dto.SaleLineDto;
import com.erich.management.Dto.SupplierOrderLineDto;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto articleDto);

    ArticleDto findById(Long id);

    ArticleDto findByCodeArticle(String code);

    List<ArticleDto> findAll();

    void delete(Long id);

    List<SaleLineDto> findHistorySales(Long idArticle);

    List<ClientOrderLineDto> findHistoryClientOrder(Long idArticle);

    List<SupplierOrderLineDto> findHistorySupplierOrder(Long idArticle);

    List<ArticleDto> findAllArticleByIdCategory(Long idCategory);


}
