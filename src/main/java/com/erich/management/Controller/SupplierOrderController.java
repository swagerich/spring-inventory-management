package com.erich.management.Controller;

import com.erich.management.Dto.SupplierOrderDto;
import com.erich.management.Services.Impl.SupplierOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SupplierOrderController {

    private final SupplierOrderServiceImpl supplierOrderService;

    @GetMapping("/supplier/all")
    public ResponseEntity<List<SupplierOrderDto>> all() {
        return ResponseEntity.ok(supplierOrderService.findAll());
    }

    @PostMapping("/supplier/save")
    public ResponseEntity<?> save(@RequestBody SupplierOrderDto supplierOrderDto) {
        return new ResponseEntity<>(supplierOrderService.save(supplierOrderDto), HttpStatus.CREATED);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id) {
        return new ResponseEntity<>(supplierOrderService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/supplier/{code}")
    public ResponseEntity<?> findByID(@PathVariable String code) {
        return new ResponseEntity<>(supplierOrderService.findSupplierByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        supplierOrderService.delete(id);
        return ResponseEntity.ok().build();
    }

}
