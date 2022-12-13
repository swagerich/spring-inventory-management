package com.erich.management.Controller;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Services.Impl.ArticleServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @GetMapping(value = "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> listArticles() {
        return articleService.findAll();
    }

    @PostMapping(value = "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody ArticleDto articleDto) {
        return new ResponseEntity<>(articleService.save(articleDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/articles/filter/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCodeArticle(@PathVariable String id) {
        return new ResponseEntity<>(articleService.findByCodeArticle(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/article/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id){
        articleService.delete(id);
    }
}
