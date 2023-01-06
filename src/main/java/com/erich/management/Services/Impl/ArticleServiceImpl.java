package com.erich.management.Services.Impl;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Dto.SaleLineDto;
import com.erich.management.Dto.SupplierOrderLineDto;
import com.erich.management.Entity.Article;
import com.erich.management.Entity.ClientOrderLine;
import com.erich.management.Entity.SaleLine;
import com.erich.management.Entity.SupplierOrderLine;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.*;
import com.erich.management.Services.ArticleService;
import com.erich.management.Validators.ArticleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepo;

    private final SaleLineRepository saleLineRepo;

    private final ClientOrderLineRepository clientOrderLineRepo;

    private final SupplierOrderLineRepository supplierOrderLineRepo;

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
        if(id == null){
            log.error("Article ID is null");
            return null;
        }
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

        List<ClientOrderLine> clientOrderLines = clientOrderLineRepo.findAllByArticleId(id);
        if(!clientOrderLines.isEmpty()){
            throw new InvalidOperationException("No se puede eliminar el cliente order, esta en uso!",ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepo.findAllByArticleId(id);
        if(!supplierOrderLines.isEmpty()){
            throw new InvalidOperationException("No se puede eliminar el proveedor orden, esta en uso!",ErrorCodes.SUPPLIER_ALREADY_IN_USE);
        }
        List<SaleLine> saleLines = saleLineRepo.findAllByArticleId(id);
        if(!saleLines.isEmpty()){
            throw new InvalidOperationException("No se puede eliminar la venta, esta en uso!",ErrorCodes.SALES_ALREADY_IN_USE);
        }

        if(!articleRepo.existsById(id)){
            throw  new EntityNotFoundException("El articulo con el " + id +" a eliminar no existe!",ErrorCodes.ARTICLE_NOT_FOUND);
        }
        articleRepo.deleteById(id);
    }

    @Override
    public List<SaleLineDto> findHistorySales(Long idArticle) {
        return saleLineRepo.findAllByArticleId(idArticle)
                .stream()
                .map(saleLine -> SaleLineDto.fromEntity(saleLine))
                .toList();
    }

    @Override
    public List<ClientOrderLineDto> findHistoryClientOrder(Long idArticle) {
        return clientOrderLineRepo.findAllByArticleId(idArticle)
                .stream()
                .map(clientOrderLine -> ClientOrderLineDto.fromEntity(clientOrderLine))
                .toList();
    }

    @Override
    public List<SupplierOrderLineDto> findHistorySupplierOrder(Long idArticle) {
        return supplierOrderLineRepo.findAllByArticleId(idArticle)
                .stream()
                .map(supplierOrderLine -> SupplierOrderLineDto.fromEntity(supplierOrderLine))
                .toList();
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Long idCategory) {
        return articleRepo.findAllByCategoryId(idCategory)
                .stream()
                .map(article -> ArticleDto.fromEntity(article))
                .toList();
    }


}
