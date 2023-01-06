package com.erich.management.Controller;

import com.erich.management.Dto.StockDto;
import com.erich.management.Entity.Stock;
import com.erich.management.Services.Impl.StockServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class StockController {

    private final StockServiceImpl stockService;

    public StockController(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock/stockreal/{idArticle}")
    public ResponseEntity<BigDecimal> stockRealArticle(@PathVariable Long idArticle) {
        return new ResponseEntity<>(stockService.stockRealArticle(idArticle), HttpStatus.OK);
    }

    @GetMapping("/stock/filter/article/{idArticle}")
    public ResponseEntity<List<StockDto>> mvtStockArticle(@PathVariable Long idArticle) {
        return new ResponseEntity<>(stockService.mvtStockArticle(idArticle), HttpStatus.OK);
    }

    @PostMapping("/stock/entry")
    public ResponseEntity<StockDto> mvtStockEntry(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.stockEntry(stockDto), HttpStatus.CREATED);
    }

    @PostMapping("/stock/sortie")
    public ResponseEntity<StockDto> mvtStockSortie(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.stockSortie(stockDto), HttpStatus.CREATED);
    }

    @PostMapping("/stock/correctionstockpos")
    public ResponseEntity<StockDto> correctionStockPos(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.correctionStockPos(stockDto), HttpStatus.CREATED);
    }

    @PostMapping("/stock/correctionstockneg")
    public ResponseEntity<StockDto> correctionStockNeg(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.correctionStockNeg(stockDto), HttpStatus.CREATED);
    }

}
