package com.erich.management.Services.Impl;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Dto.SaleLineDto;
import com.erich.management.Dto.SalesDto;
import com.erich.management.Dto.StockDto;
import com.erich.management.Entity.Article;
import com.erich.management.Entity.SaleLine;
import com.erich.management.Entity.Sales;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.ArticleRepository;
import com.erich.management.Repository.SaleLineRepository;
import com.erich.management.Repository.SalesRepository;
import com.erich.management.Services.SalesService;
import com.erich.management.Services.StockService;
import com.erich.management.Utils.Enum.SourceStock;
import com.erich.management.Utils.Enum.TypeStock;
import com.erich.management.Validators.SalesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepo;
    private final SaleLineRepository saleLineRepo;
    private final ArticleRepository articleRepo;
    private final StockService stockService;

    @Override
    public SalesDto save(SalesDto salesDto) {
        List<String> errors = SalesValidator.validator(salesDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("sales invalid", ErrorCodes.SALES_NOT_VALID, errors);
        }
        List<String> errorsArti = new ArrayList<>();
        if (salesDto.getSaleLines() != null) {
            salesDto.getSaleLines().forEach(lines -> {
                if (lines.getArticle() != null) {
                    Optional<Article> byId = articleRepo.findById(lines.getArticle().getId());
                    if (byId.isEmpty()) {
                        errorsArti.add("no items with L ID" + lines.getArticle().getId() + " not exits bd");
                    }
                }
            });
            if (!errorsArti.isEmpty()) {
                throw new InvalidEntityException("One or several articles were not found in the db", ErrorCodes.SALES_NOT_VALID, errorsArti);
            }
        }

        Sales saveSales = salesRepo.save(SalesDto.toEntity(salesDto));

        salesDto.getSaleLines().forEach(saveLine -> {
            SaleLine saleLine = SaleLineDto.toEntity(saveLine);
            saleLine.setSale(saveSales);
            saleLineRepo.save(saleLine);
            updateStock(saleLine);
        });

        return SalesDto.fromEntity(saveSales);
    }


    @Override
    public SalesDto findById(Long id) {

        return salesRepo.findById(id)
                .filter(sales -> sales.getId() != null)
                .map(sales -> SalesDto.fromEntity(sales))
                .orElseThrow(() -> new EntityNotFoundException("No sale found in the database", ErrorCodes.SALES_NOT_FOUND));
    }

    @Override
    public SalesDto findSaleByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Sales ID null");
            return null;
        }
        return salesRepo.findSalesByCode(code)
                .map(sales -> SalesDto.fromEntity(sales))
                .orElseThrow(() -> new EntityNotFoundException("No customer sales were found with the CODE", ErrorCodes.SALES_NOT_VALID));
    }

    @Override
    public List<SalesDto> findAll() {

        return Streamable.of(salesRepo.findAll()).stream()
                .map(sales -> SalesDto.fromEntity(sales)).toList();
    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Sales ID is null");
            return;
        }
        List<SaleLine> saleLines = saleLineRepo.findAllBySaleId(id);
        if (!saleLines.isEmpty()) {
            throw new InvalidOperationException("Can't delete a sale ...",
                    ErrorCodes.SALES_ALREADY_IN_USE);
        }
        salesRepo.deleteById(id);
    }

    private void updateStock(SaleLine saleLine) {
        StockDto stockDto = StockDto.builder()
                .article(ArticleDto.fromEntity(saleLine.getArticle()))
                .dateStock(Instant.now())
                .typeStock(TypeStock.SORTIE)
                .sourceStock(SourceStock.SALE)
                .quantity(saleLine.getQuantity())
                .idEntreprise(saleLine.getIdEntreprise())
                .build();

        stockService.stockSortie(stockDto);
    }
}
