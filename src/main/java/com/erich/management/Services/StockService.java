package com.erich.management.Services;

import com.erich.management.Dto.StockDto;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {

    BigDecimal stockRealArtice(Long idArticle);

    List<StockDto> mvtStockArticle(Long idArticle);

    StockDto stockEntry(StockDto stockDto);

    StockDto  stockSortie(StockDto stockDto);

    StockDto correctionStockPos(StockDto stockDto);

    StockDto correctionStockNeg(StockDto stockDto);
}
