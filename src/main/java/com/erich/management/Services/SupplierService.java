package com.erich.management.Services;

import com.erich.management.Dto.SupplierDto;

import java.util.List;

public interface SupplierService {

    SupplierDto save(SupplierDto supplierDto);

    SupplierDto findById(Long id);

    List<SupplierDto> findAll();

    void delete(Long id);
}
