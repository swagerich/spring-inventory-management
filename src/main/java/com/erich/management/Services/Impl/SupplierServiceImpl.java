package com.erich.management.Services.Impl;

import com.erich.management.Dto.ClientOrderDto;
import com.erich.management.Dto.ClientOrderLineDto;
import com.erich.management.Dto.SupplierDto;
import com.erich.management.Entity.*;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.SupplierOrderRepository;
import com.erich.management.Repository.SupplierRepository;
import com.erich.management.Services.SupplierService;
import com.erich.management.Validators.ClientOrdeValidator;
import com.erich.management.Validators.SupplierValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final SupplierOrderRepository supplierOrderRepo;

    @Override
    public SupplierDto save(SupplierDto supplierDto) {
        List<String> errors = SupplierValidator.validator(supplierDto);
        if (!errors.isEmpty()) {
            log.error("The provider is not valid {}", supplierDto);
            throw new InvalidEntityException("The provider is not valid", ErrorCodes.SUPPLIER_NOT_VALID);
        }
        return SupplierDto.fromEntity(supplierRepo.save(SupplierDto.toEntity(supplierDto)));
    }

    @Override
    public SupplierDto findById(Long id) {
        if (id == null) {
            log.error("Supplier ID is null");
            return null;
        }
        return supplierRepo.findById(id)
                .map(supplier -> SupplierDto.fromEntity(supplier))
                .orElseThrow(() -> new EntityNotFoundException("No provider with id = " + id + " was found in the database", ErrorCodes.SUPPLIER_NOT_FOUND));
    }

    @Override
    public List<SupplierDto> findAll() {
        return Streamable.of(supplierRepo.findAll())
                .stream()
                .map(supplier -> SupplierDto.fromEntity(supplier))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Supplier ID is null");
            return;
        }
        List<ClientOrder> clientOrders = supplierOrderRepo.findAllBySupplierId(id);
        if (!clientOrders.isEmpty()) {
            throw new InvalidOperationException("Cannot delete a supplier that already has orders", ErrorCodes.SUPPLIER_ORDER_ALREADY_IN_USE);
        }
        supplierRepo.deleteById(id);
    }
}
