package com.erich.management.Controller;

import com.erich.management.Dto.SalesDto;
import com.erich.management.Dto.UserDto;
import com.erich.management.Services.Impl.SalesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SalesController {

    private final SalesServiceImpl salesService;


    @GetMapping("/sales/all")
    public ResponseEntity<List<SalesDto>> all() {
        return ResponseEntity.ok(salesService.findAll());
    }

    @PostMapping("/sales/save")
    public ResponseEntity<?> save(@RequestBody SalesDto salesDto) {
        return new ResponseEntity<>(salesService.save(salesDto), HttpStatus.CREATED);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(salesService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/sales/{code}")
    public ResponseEntity<?> findBySaleCod(@PathVariable String code) {
        return new ResponseEntity<>(salesService.findSaleByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        salesService.delete(id);
        return ResponseEntity.ok().build();
    }
}
