package com.erich.management.Services.Impl;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Dto.SaleLineDto;
import com.erich.management.Dto.SupplierOrderLineDto;
import com.erich.management.Entity.Article;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Repository.ArticleRepository;
import com.erich.management.Services.ArticleService;
import com.erich.management.Validators.ArticleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepo;

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        List<String> errors = ArticleValidator.validate(articleDto);
        if (!errors.isEmpty()) {
            log.error("Articulo invalido {}", articleDto);
            throw new InvalidEntityException("Articulo no valido", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticleDto.fromEntity(articleRepo.save(ArticleDto.toEntity(articleDto)));
    }

    @Override
    public ArticleDto findById(Long id) {
        return articleRepo.findById(id).map(article -> ArticleDto.fromEntity(article)).orElseThrow(() ->
                new EntityNotFoundException("No se encontro ningun articulo con el = " + id + "en la bd", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByCodeArticle(String code) {

        Optional<Article> codeArticle = articleRepo.findArticleByCodeArticle(code);
        return Optional.of(ArticleDto.fromEntity(codeArticle.get())).orElseThrow(() ->
                new EntityNotFoundException("Articulo con ningun codigo" + code + "En la bd", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {
        //StreamSupport.stream(articleRepo.findAll().spliterator(), false)
        return Streamable.of(articleRepo.findAll())
                .map(article -> ArticleDto.fromEntity(article))
                .toList();
    }

    @Override
    public void delete(Long id) {

        if(id == null){
            log.error("Articulo id no existe {}",id);
            return;
        }
        if(!articleRepo.existsById(id)){
            throw  new EntityNotFoundException("El articulo con el " + id +" a eliminar no existe!",ErrorCodes.ARTICLE_NOT_FOUND);
        }
        articleRepo.deleteById(id);
    }

    @Override
    public List<SaleLineDto> findHistorySales(Long idArticle) {
        return null;
    }

    @Override
    public List<ClientOrderLineDto> findHistoryClientOrder(Long idArticle) {
        return null;
    }

    @Override
    public List<SupplierOrderLineDto> findHistorySupplierOrder(Long idArticle) {
        return null;
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Long idCategory) {
        return null;
    }


}
