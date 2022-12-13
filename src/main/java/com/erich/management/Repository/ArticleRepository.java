package com.erich.management.Repository;

import com.erich.management.Entity.Article;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article,Long>{

    Optional<Article> findArticleByCodeArticle(String code);

    List<Article> findAllByCategoryId(Long idCategory);
}
