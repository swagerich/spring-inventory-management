package com.erich.management.Controller;

import com.erich.management.Controller.Api.ArticleApi;
import com.erich.management.Dto.ArticleDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Dto.SaleLineDto;
import com.erich.management.Dto.SupplierOrderLineDto;
import com.erich.management.Services.Impl.ArticleServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class ArticleController implements ArticleApi {

    private final ArticleServiceImpl articleService;

    @Override
    public List<ArticleDto> listArticles() {
        return articleService.findAll();
    }

    @Override
    public ResponseEntity<?> save(@RequestBody ArticleDto articleDto) {
        return new ResponseEntity<>(articleService.save(articleDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> search(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findCodeArticle(@PathVariable String id) {
        return new ResponseEntity<>(articleService.findByCodeArticle(id), HttpStatus.OK);
    }

    @Override
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }

    @Override
    public ResponseEntity<List<SaleLineDto>> findHistorySales(Long idArticle) {
        return new ResponseEntity<>(articleService.findHistorySales(idArticle), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClientOrderLineDto>> findHistoryClientOrder(Long idArticle) {
        return new ResponseEntity<>(articleService.findHistoryClientOrder(idArticle), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SupplierOrderLineDto>> findHistorySupplierOrder(Long idArticle) {
        return new ResponseEntity<>(articleService.findHistorySupplierOrder(idArticle), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ArticleDto>> findAllArticleByIdCategory(Long idCategory) {
        return new ResponseEntity<>(articleService.findAllArticleByIdCategory(idCategory), HttpStatus.OK);
    }
}
