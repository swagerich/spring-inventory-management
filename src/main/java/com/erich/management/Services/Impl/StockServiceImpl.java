package com.erich.management.Services.Impl;

import com.erich.management.Dto.StockDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Repository.StockRepository;
import com.erich.management.Services.ArticleService;
import com.erich.management.Services.StockService;
import com.erich.management.Utils.Enum.TypeStock;
import com.erich.management.Validators.StockValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepo;
    private final ArticleService articleService;

    @Override
    public BigDecimal stockRealArtice(Long idArticle) {
        if(idArticle == null){
            log.error("ID article is null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return stockRepo.stockRealArticle(idArticle);
    }

    @Override
    public List<StockDto> mvtStockArticle(Long idArticle) {

        return stockRepo.findAllByArticleId(idArticle)
                .stream()
                .map(stock -> StockDto.fromEntity(stock))
                .toList();
    }

    @Override
    public StockDto stockEntry(StockDto stockDto) {
        return entryPositive(stockDto, TypeStock.ENTRY);
    }

    @Override
    public StockDto stockSortie(StockDto stockDto) {
        return sortieNegative(stockDto,TypeStock.SORTIE);
    }


    @Override
    public StockDto correctionStockPos(StockDto stockDto) {
        return entryPositive(stockDto,TypeStock.CORRECTION_POS);
    }

    @Override
    public StockDto correctionStockNeg(StockDto stockDto) {
        return sortieNegative(stockDto,TypeStock.CORRECTION_NEG);
    }

    private StockDto sortieNegative(StockDto stockDto, TypeStock type) {
        List<String> errors = StockValidator.validator(stockDto);
        if(!errors.isEmpty()){
            throw  new InvalidEntityException("The inventory movement is not valid", ErrorCodes.STOCK_NOT_VALID);
        }
        stockDto.setQuantity(BigDecimal.valueOf(
                stockDto.getQuantity().doubleValue() * -1) );
        stockDto.setTypeStock(type);

        return StockDto.fromEntity(stockRepo.save(StockDto.toEntity(stockDto)));
    }

    private StockDto entryPositive(StockDto stockDto, TypeStock type) {
        List<String> errors = StockValidator.validator(stockDto);
        if(!errors.isEmpty()){
            throw  new InvalidEntityException("The inventory movement is not valid", ErrorCodes.STOCK_NOT_VALID);
        }
        stockDto.setQuantity(BigDecimal.valueOf(
                stockDto.getQuantity().doubleValue()));
        stockDto.setTypeStock(type);

        return StockDto.fromEntity(stockRepo.save(StockDto.toEntity(stockDto)));
    }

}
