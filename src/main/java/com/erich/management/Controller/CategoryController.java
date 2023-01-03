package com.erich.management.Controller;

import com.erich.management.Dto.CategoryDto;
import com.erich.management.Services.Impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/category/all")
    public ResponseEntity<List<CategoryDto>> all() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/category/save")
    public ResponseEntity<?> save(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.save(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/category/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code) {
        return new ResponseEntity<>(categoryService.findByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> delet(@PathVariable Long id){
        categoryService.delete(id);
        return  ResponseEntity.ok().build();
    }

}
