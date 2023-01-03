package com.erich.management.Services;

import com.erich.management.Dto.EnterpriseDto;

import java.util.List;


public interface EnterpriseService {

    EnterpriseDto save(EnterpriseDto enterpriseDto);

    EnterpriseDto findById(Long id);

    List<EnterpriseDto> findAll();

    void delete(Long id);
}
