package com.erich.management.Services.Impl;

import com.erich.management.Dto.CategoryDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;


    @Test
    void save() {
        CategoryDto expectedCategory = CategoryDto.builder().code("developeando test")
                .description("descp test")
                .idEntreprise(1L)
                .build();
        CategoryDto save = categoryService.save(expectedCategory);

        Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getId());
        Assertions.assertEquals(expectedCategory.getCode(), save.getCode());
        Assertions.assertEquals(expectedCategory.getDescription(), save.getDescription());
        Assertions.assertEquals(expectedCategory.getIdEntreprise(), save.getIdEntreprise());
    }

    @Test
    void findById() {
        CategoryDto expectedCategory = CategoryDto.builder()
                .id(5L)
                .code("developeando test")
                .description("descp test")
                .idEntreprise(1L)
                .build();


        CategoryDto save = categoryService.save(expectedCategory);

        categoryService.findById(save.getId());

      //  Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getId());
       // Assertions.assertEquals(expectedCategory.getId(), save.getId());
        Assertions.assertEquals(expectedCategory.getCode(), save.getCode());
        Assertions.assertEquals(expectedCategory.getDescription(), save.getDescription());
        Assertions.assertEquals(expectedCategory.getIdEntreprise(), save.getIdEntreprise());
    }

    @Test
    void findByCode() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void shoulThrowInvalidEntityException(){
        CategoryDto expect = CategoryDto.builder().build();

        InvalidEntityException expectdException = Assertions.assertThrows(InvalidEntityException.class,()-> categoryService.save(expect));

        Assertions.assertEquals(ErrorCodes.CATEGORY_NOT_VALID,expectdException.getErrorCodes());
        Assertions.assertEquals(1,expectdException.getErrors().size());
        Assertions.assertEquals("Por favor ingrese el código de categoría",expectdException.getErrors().get(0));

    }
}