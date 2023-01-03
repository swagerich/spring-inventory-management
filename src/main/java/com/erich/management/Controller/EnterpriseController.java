package com.erich.management.Controller;

import com.erich.management.Dto.EnterpriseDto;
import com.erich.management.Dto.SupplierOrderDto;
import com.erich.management.Services.Impl.EnterpriseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EnterpriseController {

    private final EnterpriseServiceImpl enterpriseService;

    @GetMapping("/enterprise/all")
    public ResponseEntity<List<EnterpriseDto>> all() {
        return ResponseEntity.ok(enterpriseService.findAll());
    }

    @PostMapping("/enterprise/save")
    public ResponseEntity<?> save(@RequestBody EnterpriseDto enterpriseDto) {
        return new ResponseEntity<>(enterpriseService.save(enterpriseDto), HttpStatus.CREATED);
    }

    @GetMapping("/enterprise/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(enterpriseService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/enterprise/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        enterpriseService.delete(id);
        return ResponseEntity.ok().build();
    }
}
