package com.erich.management.Services;

import com.erich.management.Dto.SalesDto;

import java.util.List;

public interface SalesService {

    SalesDto save(SalesDto salesDto);

    SalesDto findById(Long id);

    SalesDto findSaleByCode(String code);

    List<SalesDto> findAll();

    void delete(Long id);
}
