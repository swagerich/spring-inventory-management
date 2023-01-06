package com.erich.management.Services.Impl;

import com.erich.management.Dto.CategoryDto;
import com.erich.management.Entity.Article;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.ArticleRepository;
import com.erich.management.Repository.CategoryRepository;
import com.erich.management.Services.CategoryService;
import com.erich.management.Validators.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final ArticleRepository articleRepo;

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Categoria invalida", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(categoryRepo.save(CategoryDto.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepo.findById(id).filter(ids -> ids.getId() != null)
                .map(category -> CategoryDto.fromEntity(category))
                .orElseThrow(() -> new EntityNotFoundException("La categoria no existe con el id = " + id + "en la bd", ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryDto findByCode(String code) {

        return categoryRepo.findCategoryByCode(code).filter(catego -> StringUtils.hasLength(catego.getCode()))
                .map(category -> CategoryDto.fromEntity(category))
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con su codigo = " + code, ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAll() {
        return Streamable.of(categoryRepo.findAll())
                .stream()
                .map(category -> CategoryDto.fromEntity(category))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            return;
        }
        List<Article> articles = articleRepo.findAllByCategoryId(id);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("No se puede eliminar esta categoria , esta en uso", ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepo.deleteById(id);
    }
}
