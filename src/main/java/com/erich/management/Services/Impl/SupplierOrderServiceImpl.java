package com.erich.management.Services.Impl;

import com.erich.management.Dto.*;
import com.erich.management.Entity.*;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.*;
import com.erich.management.Services.StockService;
import com.erich.management.Services.SupplierOrderService;
import com.erich.management.Utils.Enum.OrdeStatus;
import com.erich.management.Utils.Enum.SourceStock;
import com.erich.management.Utils.Enum.TypeStock;
import com.erich.management.Validators.ArticleValidator;
import com.erich.management.Validators.SupplierOrdeValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepo;
    private final SupplierOrderLineRepository supplierOrderLineRepo;
    private final SupplierRepository supplierRepo;
    private final ArticleRepository articleRepo;
    private final StockService stockService;

    @Override
    public SupplierOrderDto save(SupplierOrderDto supplierOrderDto) {

        List<String> errors = SupplierOrdeValidator.validate(supplierOrderDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("the Supplier's request is not valid", ErrorCodes.SUPPLIER_ORDER_NOT_vALID, errors);
        }

        if (supplierOrderDto.getId() != null && supplierOrderDto.isOrderDelivered()) {
            throw new InvalidEntityException("Cannot change the order when it is delivered", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }

        Optional<Supplier> supplier = supplierRepo.findById(supplierOrderDto.getSupplier().getId());
        if (supplier.isEmpty()) {
            throw new InvalidEntityException("No customer with ID in the bd", ErrorCodes.SUPPLIER_NOT_FOUND);
        }


        List<String> articleError = new ArrayList<>();

        if (supplierOrderDto.getSupplierOrderLine() != null) {

            supplierOrderDto.getSupplierOrderLine().forEach(lineSupplier -> {
                if (lineSupplier.getArticle() != null) {
                    Optional<Article> article = articleRepo.findById(lineSupplier.getArticle().getId());
                    if (article.isEmpty()) {
                        articleError.add("article with ID" + lineSupplier.getArticle().getId() + "not Exist");
                    }
                } else {
                    articleError.add("Cannot save an order with a NULL item");
                }
            });
        }
        if (!articleError.isEmpty()) {
            throw new InvalidEntityException("No item with in the bd", ErrorCodes.ARTICLE_NOT_FOUND, articleError);
        }
        supplierOrderDto.setDateOrder(Instant.now());
        SupplierOrder saveSupplierOrder = supplierOrderRepo.save(SupplierOrderDto.toEntity(supplierOrderDto));

        if (supplierOrderDto.getSupplierOrderLine() != null) {

            supplierOrderDto.getSupplierOrderLine().forEach(line -> {
                SupplierOrderLine supplierOrderLine = SupplierOrderLineDto.toEntity(line);
                supplierOrderLine.setSupplierOrder(saveSupplierOrder);
                supplierOrderLine.setIdEntreprise(saveSupplierOrder.getIdEntreprise());
                SupplierOrderLine saveLine = supplierOrderLineRepo.save(supplierOrderLine);

                bringIn(saveLine);
            });
        }

        return SupplierOrderDto.fromEntity(saveSupplierOrder);
    }

    @Override
    public SupplierOrderDto updateOrderStatus(Long idOrder, OrdeStatus ordeStatus) {
        checkIdOrder(idOrder);
        if (!StringUtils.hasLength(String.valueOf(ordeStatus))) {
            log.error("Vendor order status is NULL");
            throw new InvalidOperationException("Cannot change order status with null status", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
        SupplierOrderDto supplierOrderDto = checkOrderStatus(idOrder);
        supplierOrderDto.setOrdeStatus(ordeStatus);

        SupplierOrder saveSuOrder = supplierOrderRepo.save(SupplierOrderDto.toEntity(supplierOrderDto));
        if (supplierOrderDto.isOrderDelivered()) {
            updateStock(idOrder);
        }
        return SupplierOrderDto.fromEntity(saveSuOrder);
    }

    @Override
    public SupplierOrderDto updateQuantityOrder(Long idOrder, Long idOrderLine, BigDecimal quantity) {
        checkIdOrder(idOrder);
        checkIdOrderLine(idOrderLine);

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            log.error("The order line id is NULL");
            throw new InvalidOperationException("The status of the order cannot be changed with null or ZERO quantity",
                    ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }

        SupplierOrderDto supplierOrder = checkOrderStatus(idOrder);
        Optional<SupplierOrderLine> supplierOrderLineOptional = findSupplierOrderLine(idOrderLine);

        if (supplierOrderLineOptional.isPresent()) {
            SupplierOrderLine supplierOrderLine = supplierOrderLineOptional.get();
            supplierOrderLine.setQuantity(quantity);

            supplierOrderLineRepo.save(supplierOrderLine);
        }
        return supplierOrder;
    }

    @Override
    public SupplierOrderDto updateSupplier(Long idOrder, Long idSupplier) {
        checkIdOrder(idOrder);
        if (idSupplier == null) {
            log.error("Supplier ID is null");
            throw new InvalidOperationException("Cannot change order status with null vendor id", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
        SupplierOrderDto supplierOrder = checkOrderStatus(idOrder);
        Optional<Supplier> supplierOptional = supplierRepo.findById(idSupplier);
        if (supplierOptional.isEmpty()) {
            throw new EntityNotFoundException("No provider with ID found" + idSupplier, ErrorCodes.SUPPLIER_NOT_FOUND);
        }
        supplierOrder.setSupplier(SupplierDto.fromEntity(supplierOptional.get()));

        return SupplierOrderDto.fromEntity(supplierOrderRepo.save(SupplierOrderDto.toEntity(supplierOrder)));
    }

    @Override
    public SupplierOrderDto updateArticle(Long idOrder, Long idOrderLine, Long idArticle) {
        checkIdOrder(idOrder);
        checkIdOrderLine(idOrderLine);
        checkIdArticle(idArticle, "new");

        SupplierOrderDto commandeFournisseur = checkOrderStatus(idOrder);

        Optional<SupplierOrderLine> supplierOrderLine = findSupplierOrderLine(idOrderLine);

        Optional<Article> articleOptional = articleRepo.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No article found with the ID" + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        if (supplierOrderLine.isPresent()) {
            SupplierOrderLine saveSupplierOrderLine = supplierOrderLine.get();
            saveSupplierOrderLine.setArticle(articleOptional.get());
            supplierOrderLineRepo.save(saveSupplierOrderLine);
        }

        return commandeFournisseur;
    }

    @Override
    public SupplierOrderDto deleteArticle(Long idOrder, Long idOrderLine) {
        checkIdOrder(idOrder);
        checkIdOrderLine(idOrderLine);

        // Solo para comprobar la línea de pedido del proveedor e informar al proveedor en caso de que esté ausente
        SupplierOrderDto supplierOrderDto = checkOrderStatus(idOrder);
        findSupplierOrderLine(idOrderLine);
        supplierOrderRepo.deleteById(idOrderLine);

        return supplierOrderDto;
    }

    @Override
    public SupplierOrderDto findById(Long id) {
        if (id == null) {
            log.error("Supplier order ID is null");
            return null;
        }
        return supplierOrderRepo.findById(id)
                .filter(ids -> ids.getId() != null)
                .map(supplierOrder -> SupplierOrderDto.fromEntity(supplierOrder))
                .orElseThrow(() -> new EntityNotFoundException("No sales order with ID found" + id, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND));

    }

    @Override
    public SupplierOrderDto findSupplierByCode(String code) {

        if (!StringUtils.hasLength(code)) {
            log.error("Supplier Order CODE is null");
            return null;
        }
        return supplierOrderRepo.findSupplierOrderByCode(code)
                .filter(cod -> cod.getCode() != null)
                .map(supplierOrder -> SupplierOrderDto.fromEntity(supplierOrder))
                .orElseThrow(() -> new EntityNotFoundException("No sales order with code found" + code, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND));

    }

    @Override
    public List<SupplierOrderDto> findAll() {
        return Streamable.of(supplierOrderRepo.findAll())
                .stream()
                .map(supplierOrder -> SupplierOrderDto.fromEntity(supplierOrder))
                .toList();
    }

    @Override
    public List<SupplierOrderLineDto> findAllSupplierOrderLineBySupplierOrderId(Long id) {

        return supplierOrderLineRepo.findAllBySupplierOrderId(id)
                .stream()
                .map(line -> SupplierOrderLineDto.fromEntity(line))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            log.error("ID IS NULL");
            return;
        }
        List<SupplierOrderLine> orderLines = supplierOrderLineRepo.findAllBySupplierOrderId(id);
        if (!orderLines.isEmpty()) {
            throw new InvalidOperationException("Cannot delete an already used sales order", ErrorCodes.CLIENT_ORDER_ALREADY_IN_USE);
        }
            supplierOrderRepo.deleteById(id);
    }

    private Optional<SupplierOrderLine> findSupplierOrderLine(Long id) {
        Optional<SupplierOrderLine> supplierOrderLineOptional = supplierOrderLineRepo.findById(id);
        if (supplierOrderLineOptional.isEmpty()) {
            throw new EntityNotFoundException("No PO line with ID found" + id, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND);
        }
        return supplierOrderLineOptional;
    }

    private void checkIdOrder(Long idOrder) {
        if (idOrder == null) {
            log.error("Supplier order ID null");
            throw new InvalidOperationException("Cannot change order status with null ID", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
    }

    private SupplierOrderDto checkOrderStatus(Long idOrder) {
        SupplierOrderDto supplierOrder = findById(idOrder);
        if (supplierOrder.isOrderDelivered()) {
            throw new InvalidOperationException("Cannot change the order when it is delivered", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
        return supplierOrder;
    }

    private void checkIdOrderLine(Long idOrder) {
        if (idOrder == null) {
            log.error("The Id SupplierOrderLine Is NULL");
            throw new InvalidOperationException("Cannot change order status with null command line", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
    }

    private void checkIdArticle(Long idArticle, String msg) {
        if (idArticle == null) {
            log.error("ID with " + msg + "is Null");
            throw new InvalidOperationException("", ErrorCodes.SUPPLIER_ORDER_NOT_MODIFIABLE);
        }
    }

    private void updateStock(Long idOrder) {
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepo.findAllBySupplierOrderId(idOrder);
        supplierOrderLines.forEach(this::bringIn);
    }

    private void bringIn(SupplierOrderLine supplierOrderLine) {
        StockDto stockDto = StockDto.builder()
                .article(ArticleDto.fromEntity(supplierOrderLine.getArticle()))
                .dateStock(Instant.now())
                .typeStock(TypeStock.ENTRY)
                .sourceStock(SourceStock.ORDE_SUPPLIER)
                .quantity(supplierOrderLine.getQuantity())
                .idEntreprise(supplierOrderLine.getIdEntreprise())
                .build();
        stockService.stockEntry(stockDto);
    }
}
