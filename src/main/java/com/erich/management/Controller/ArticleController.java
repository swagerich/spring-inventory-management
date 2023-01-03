package com.erich.management.Controller;

import com.erich.management.Controller.Api.ArticleApi;
import com.erich.management.Dto.ArticleDto;
import com.erich.management.Services.Impl.ArticleServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.management.Utils.Constants.Path.APP_ROOT;
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
}
